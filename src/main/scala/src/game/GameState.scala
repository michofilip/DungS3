package src.game

import src.game.gameobject.EntityRepository
import src.game.event.Event
import src.game.temporal.Timer

import scala.collection.immutable.Queue
import scala.xml.Node

// TODO turn, etc
final class GameState(val timer: Timer, val entities: EntityRepository, val events: Queue[Event]):

    def updated(timer: Timer = timer, entities: EntityRepository = entities, events: Queue[Event] = events): GameState =
        GameState(
            timer = timer,
            entities = entities,
            events = events
        )

    def addEvents(events: Seq[Event]): GameState =
        updated(events = this.events ++ events)

    override def toString: String =
        val eventsStr = events.mkString("[", ", ", "]")
        s"GameState(timer=$timer, entities=$entities, events=$eventsStr)"