package src

import src.data.repository.impl.{MockEntityPrototypeRepositoryImpl, MockGraphicsSelectorRepositoryImpl, MockPhysicsSelectorRepositoryImpl}
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository, EntityService}
import src.game.event.{Event, PositionEvent}
import src.game.temporal.{Timer, Timestamp}
import src.game.{GameFrame, GameState}

import java.util.UUID

object Main:

    @main
    def start(): Unit =
        val physicsSelectorRepository = new MockPhysicsSelectorRepositoryImpl()
        val graphicsSelectorRepository = new MockGraphicsSelectorRepositoryImpl()
        val entityPrototypeRepository = new MockEntityPrototypeRepositoryImpl(physicsSelectorRepository, graphicsSelectorRepository)

        val entityService = new EntityService(entityPrototypeRepository)

        //        val entity1 = Entity(UUID.randomUUID(), "entity", None, Some(Position(10, 15)), Some(Direction.North))
        //        println(entity1)
        //        println(entity1.update(direction = DirectionMapper.TurnBack))
        val entity1 = entityService.createEntity(
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
