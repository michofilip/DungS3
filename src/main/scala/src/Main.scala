package src

import src.data.model.GameObjectEntity
import src.data.repository.{AnimationRepository, AnimationSelectorRepository, FrameRepository, GameObjectPrototypeRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.GameState
import src.game.event.Event
import src.game.gameobject.mapper.{DirectionMapper, PositionMapper}
import src.game.gameobject.parts.position.Direction
import src.game.gameobject.parts.state.State
import src.game.gameobject.{GameObject, GameObjectPrototype, GameObjectRepository}
import src.game.service.serialization.{GameObjectSerializationService, GameStateSerializationService}
import src.game.service.{EventProcessor, GameObjectConverter, GameStateFileProcessor, GameStateProcessor}
import src.game.temporal.{Duration, Timer, Timestamp}

import java.io.File
import java.util.UUID
import scala.collection.immutable.Queue

object Main:

    @main
    def start(): Unit =

        val gameObjectEntity1 = GameObjectEntity(id = UUID.randomUUID().toString, name = "player", creationTimestamp = 0L, state = None, stateTimestamp = None, x = Some(10), y = Some(20), direction = Some(Direction.East), positionTimestamp = Some(0L))
        val gameObjectEntity2 = GameObjectEntity(id = UUID.randomUUID().toString, name = "player", creationTimestamp = 0L, state = None, stateTimestamp = None, x = Some(0), y = Some(0), direction = Some(Direction.East), positionTimestamp = Some(0L))

        val gameObject1 = GameObjectConverter().fromEntity(gameObjectEntity1).get

        val event1 = Event.MoveBy(gameObjectId = gameObject1.id, dx = 10, dy = 15)
        val event2 = Event.MoveTo(gameObjectId = UUID.fromString(gameObjectEntity2.id), x = -1, y = -1)
        val event3 = Event.Spawn(useCurrentTimestamp = true, gameObjects = Seq(gameObjectEntity2), events = Seq(event2))

        val gameObjectRepository = GameObjectRepository(Seq(gameObject1))
        val gameState0 = GameState(timer = Timer(running = true), gameObjects = gameObjectRepository, events = Queue(event1, event3))
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
