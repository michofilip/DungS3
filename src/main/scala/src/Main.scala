package src

import src.data.model.EntityEntry
import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.GameState
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository}
import src.game.event.Event
import src.game.service.serialization.{EntitySerializationService, GameStateSerializationService}
import src.game.service.{EntityConverter, EventProcessor, GameStateFileProcessor, GameStateProcessor}
import src.game.temporal.{Duration, Timer, Timestamp}

import java.io.File
import java.util.UUID

object Main:

    @main
    def start(): Unit =

        val entityEntry1 = EntityEntry(id = UUID.randomUUID().toString, name = "player", timestamp = 0L, state = None, x = Some(10), y = Some(20), direction = Some(Direction.East))
        val entityEntry2 = EntityEntry(id = UUID.randomUUID().toString, name = "player", timestamp = 0L, state = None, x = Some(0), y = Some(0), direction = Some(Direction.East))

        val entity1 = EntityConverter().convertToEntity(entityEntry1).get

        val event1 = Event.MoveBy(entityId = entity1.id, dx = 10, dy = 15)
        val event2 = Event.MoveTo(entityId = UUID.fromString(entityEntry2.id), x = -1, y = -1)
        val event3 = Event.Spawn(useCurrentTimestamp = true, entities = Seq(entityEntry2), events = Seq(event2))

        val entityRepository = EntityRepository(Seq(entity1))
        val gameState0 = GameState(timer = Timer(running = true), entities = entityRepository, events = Vector(event1, event3))
        Thread.sleep(1000)
        val gameState1 = GameStateProcessor().processNextEvent(gameState0)
        Thread.sleep(1000)
        val gameState2 = GameStateProcessor().processNextEvent(gameState1)
        Thread.sleep(1000)
        val gameState3 = GameStateProcessor().processNextEvent(gameState2)

        println(gameState0)
//        println(gameState1)
//        println(gameState2)
//        println(gameState3)

        //        Thread.sleep(1000)
        GameStateFileProcessor().saveToFile(File("gameState0.xml"), gameState0)
        GameStateFileProcessor().saveToFile(File("gameState1.xml"), gameState1)
        GameStateFileProcessor().saveToFile(File("gameState2.xml"), gameState2)
        GameStateFileProcessor().saveToFile(File("gameState3.xml"), gameState3)
//        Thread.sleep(1000)
        GameStateFileProcessor().loadFromFile(File("gameState0.xml")).foreach(println)
