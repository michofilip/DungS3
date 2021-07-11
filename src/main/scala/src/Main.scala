package src

import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository, EntityService}
import src.game.event.{Event, PositionEvent}
import src.game.temporal.{Duration, Timer, Timestamp}
import src.game.{GameFrame, GameFrameFileService, GameFrameXmlService, GameState, GameStateXmlService}

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
    val gameStateXmlService = new GameStateXmlService(entityService)
    val gameFrameXmlService = new GameFrameXmlService(gameStateXmlService)
    val gameFrameFileService = new GameFrameFileService(gameFrameXmlService)

    @main
    def start(): Unit =

        val entity1 = entityService.createEntity(
            id = UUID.randomUUID(),
            name = "player",
            timestamp = Timestamp.zero,
            position = Some(Position(10, 20)),
            direction = Some(Direction.East)
        ).get

        val event = PositionEvent.MoveBy(entityId = entity1.id, dx = 10, dy = 15)

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
