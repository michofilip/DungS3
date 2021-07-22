package src.game

import src.game.entity.EntityRepository
import src.game.event.Event
import src.game.temporal.Timer

import scala.xml.Node

// TODO turn, etc
final class GameState(val timer: Timer, val entities: EntityRepository, val events: Vector[Event]):

    def updated(timer: Timer = timer, entities: EntityRepository = entities, events: Vector[Event] = events): GameState =
        GameState(
            timer = timer,
            entities = entities,
            events = events
        )

    def addEvents(events: Vector[Event]): GameState =
        updated(events = this.events ++ events)

    override def toString: String =
        val eventsStr = events.mkString("Events(", ", ", ")")
        s"GameState(timer=$timer, entities=$entities, events=$eventsStr)"