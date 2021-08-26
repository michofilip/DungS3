package src.game

import src.game.event.Event
import src.game.gameobject.GameObjectRepository
import src.game.temporal.Timer

import scala.collection.immutable.Queue
import scala.xml.Node

// TODO turn, etc
final class GameState(val timer: Timer, val gameObjects: GameObjectRepository, val events: Queue[Event]):

    def updated(timer: Timer = timer, gameObjects: GameObjectRepository = gameObjects, events: Queue[Event] = events): GameState =
        GameState(
            timer = timer,
            gameObjects = gameObjects,
            events = events
        )

    def addEvents(events: Seq[Event]): GameState =
        updated(events = this.events ++ events)

    override def toString: String =
        val eventsStr = events.mkString("[", ", ", "]")
        s"GameState(timer=$timer, gameObjects=$gameObjects, events=$eventsStr)"