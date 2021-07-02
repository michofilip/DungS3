package src

import src.data.repository.EntityPrototypeRepository
import src.data.repository.impl.{MockAnimationSelectorRepositoryImpl, MockEntityPrototypeRepositoryImpl, MockPhysicsSelectorRepositoryImpl}
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository}
import src.game.event.{Event, PositionEvent}
import src.game.temporal.{Timer, Timestamp}
import src.game.{GameFrame, GameState}

import java.util.UUID

object Main:

    @main
    def start(): Unit =
        val physicsSelectorRepository = new MockPhysicsSelectorRepositoryImpl()
        val animationSelectorRepository = new MockAnimationSelectorRepositoryImpl()

        given EntityPrototypeRepository = new MockEntityPrototypeRepositoryImpl(physicsSelectorRepository, animationSelectorRepository)

        //        val entityService = new EntityService(entityPrototypeRepository)

        //        val entity1 = Entity(UUID.randomUUID(), "entity", None, Some(Position(10, 15)), Some(Direction.North))
        //        println(entity1)
        //        println(entity1.update(direction = DirectionMapper.TurnBack))
        val entity1 = Entity.create(
            id = UUID.randomUUID(),
            name = "entity",
            position = Some(Position(10, 20)),
            timestamp = Timestamp.zero
        ).get

        val event = PositionEvent.MoveBy(entityId = entity1.id, dx = 10, dy = 15)

        val entityRepository = EntityRepository(Seq(entity1))
        val gameState = GameState(timer = Timer(running = true), entities = entityRepository)
        val gameFrame = new GameFrame(gameState = gameState, events = Vector(event))

        Thread.sleep(1000)
        println(gameFrame)
        Thread.sleep(1000)
        println(gameFrame.nextFrame())
