package src

import src.data.model.EntityEntry
import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.GameState
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.{Entity, EntityPrototype, EntityRepository}
import src.game.event.Event
import src.game.service.serialization.{EntitySerializationService, GameStateSerializationService}
import src.game.service.{EntityConverter, EventProcessor, Engine, GameStateFileProcessor, GameStateProcessor}
import src.game.temporal.{Duration, Timer, Timestamp}

import java.io.File
import java.util.UUID

object Main:

    val engine = new Engine()

    @main
    def start(): Unit =

        val entityEntry = EntityEntry(id = UUID.randomUUID().toString, name = "player", timestamp = 0L, state = None, x = Some(10), y = Some(20), direction = Some(Direction.East))

        val entity1 = engine.entityConverter.convertToEntity(entityEntry).get

        val event = Event.MoveBy(entityId = entity1.id, dx = 10, dy = 15)

        val entityRepository = EntityRepository(Seq(entity1))
        val gameState = GameState(timer = Timer(running = true), entities = entityRepository, events = Vector(event))
        //        val gameFrame = new GameFrame(gameState = gameState, events = Vector(event))

        //        Thread.sleep(1000)
        println(gameState)
        Thread.sleep(1000)
        println(engine.gameStateProcessor.processNextEvent(gameState))

        //        Thread.sleep(1000)
        engine.gameStateFileProcessor.saveToFile(File("gameState.xml"), gameState)
        //        Thread.sleep(1000)
        engine.gameStateFileProcessor.loadFromFile(File("gameState.xml")).foreach(println)
