package src

import src.data.model.EntityEntry
import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository}
import src.game.event2.Event
import src.game.service.{EntitySerializationService, EntityService, GameFrameFileService, GameFrameSerializationService, GameStateSerializationService}
import src.game.temporal.{Duration, Timer, Timestamp}
import src.game.{GameFrame, GameState}

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
    val gameStateXmlService = new GameStateSerializationService(entitySerializationService)
    val gameFrameXmlService = new GameFrameSerializationService(gameStateXmlService)
    val gameFrameFileService = new GameFrameFileService(gameFrameXmlService)

    @main
    def start(): Unit =

        val entityEntry = EntityEntry(id = UUID.randomUUID().toString, name = "player", timestamp = 0L, state = None, x = Some(10), y = Some(20), direction = Some(Direction.East))

        val entity1 = entityService.convertToEntity(entityEntry).get

        val event = Event.MoveBy(entityId = entity1.id, dx = 10, dy = 15)

        val entityRepository = EntityRepository(Seq(entity1))
        val gameState = GameState(timer = Timer(running = true), entities = entityRepository)
        val gameFrame = new GameFrame(gameState = gameState, events = Vector(event))

        //        Thread.sleep(1000)
        println(gameFrame)
        //        Thread.sleep(1000)
        //        println(gameFrame.nextFrame)

        //        Thread.sleep(1000)
        gameFrameFileService.saveToFile(File("gameFrame.xml"), gameFrame)
        //        entityService.saveEntitiesToFile(File("entities.xml"), gameState.entities.findAll)
        //        Thread.sleep(1000)
        gameFrameFileService.loadFromFile(File("gameFrame.xml")).foreach(println)
//        println(entityService.loadEntitiesFromFile(File("entities.xml")))
