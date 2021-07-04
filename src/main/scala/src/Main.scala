package src

import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository, EntityService}
import src.game.event.{Event, PositionEvent}
import src.game.temporal.{Duration, Timer, Timestamp}
import src.game.{GameFrame, GameState}

import java.io.File
import java.util.UUID

object Main:

    given frameRepository: FrameRepository = new FrameRepository

    given animationRepository: AnimationRepository = new AnimationRepository

    given animationSelectorRepository: AnimationSelectorRepository = new AnimationSelectorRepository

    given physicsRepository: PhysicsRepository = new PhysicsRepository

    given physicsSelectorRepository: PhysicsSelectorRepository = new PhysicsSelectorRepository

    given entityPrototypeRepository: EntityPrototypeRepository = new EntityPrototypeRepository

    given entityService: EntityService = new EntityService

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

        Thread.sleep(1000)
        println(gameFrame)
        Thread.sleep(1000)
        println(gameFrame.nextFrame())

        Thread.sleep(1000)
        entityService.saveEntitiesToFile(File("entities.txt"), gameState.entities.findAll)
        Thread.sleep(1000)
        println(entityService.loadEntitiesFromFile(File("entities.txt")))

        println(physicsRepository.findById(1))
        println(physicsRepository.findById(2))
        println(physicsRepository.findById(3))
        println(physicsRepository.findById(4))

        println(physicsSelectorRepository.findById(1).map(_.selectPhysics(None)))

        println(frameRepository.findById(1))

        println(animationRepository.findById(1).map(_.frame(Duration.zero)))
        println(animationRepository.findById(2).map(_.frame(Duration.zero)))

        println(animationSelectorRepository.findById(1).map(_.selectAnimation(None,None)))
