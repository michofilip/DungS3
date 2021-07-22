package src

import src.data.model.EntityEntry
import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.GameState
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository}
import src.game.event.Event
import src.game.service.{EntitySerializationService, EntityService, EventProcessorService, GameStateFileService, GameStateSerializationService, GameStateService}
import src.game.temporal.{Duration, Timer, Timestamp}

import java.io.File
import java.util.UUID

object Main:

    val frameRepository = new FrameRepository
    val animationRepository = new AnimationRepository(frameRepository)
    val animationSelectorRepository = new AnimationSelectorRepository(animationRepository)
    val physicsRepository = new PhysicsRepository
    val physicsSelectorRepository = new PhysicsSelectorRepository(physicsRepository)
    val entityPrototypeRepository = new EntityPrototypeRepository(physicsSelectorRepository, animationSelectorRepository)
    val entityService = new EntityService(entityPrototypeRepository)
    val entitySerializationService = new EntitySerializationService(entityService)
    val eventProcessor = new EventProcessorService(entityService)
    val gameStateSerializationService = new GameStateSerializationService(entitySerializationService)
    val gameStateFileService = new GameStateFileService(gameStateSerializationService)
    val gameStateService = new GameStateService(eventProcessor)
    //    val gameFrameXmlService = new GameFrameSerializationService(gameStateXmlService)
    //    val gameFrameFileService = new GameFrameFileService(gameFrameXmlService)

    @main
    def start(): Unit =

        val entityEntry = EntityEntry(id = UUID.randomUUID().toString, name = "player", timestamp = 0L, state = None, x = Some(10), y = Some(20), direction = Some(Direction.East))

        val entity1 = entityService.convertToEntity(entityEntry).get

        val event = Event.MoveBy(entityId = entity1.id, dx = 10, dy = 15)

        val entityRepository = EntityRepository(Seq(entity1))
        val gameState = GameState(timer = Timer(running = true), entities = entityRepository, events = Vector(event))
        //        val gameFrame = new GameFrame(gameState = gameState, events = Vector(event))

        //        Thread.sleep(1000)
        println(gameState)
        Thread.sleep(1000)
        println(gameStateService.processNextEvent(gameState))

        //        Thread.sleep(1000)
        gameStateFileService.saveToFile(File("gameState.xml"), gameState)
        //        Thread.sleep(1000)
        gameStateFileService.loadFromFile(File("gameState.xml")).foreach(println)
