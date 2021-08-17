package src.data.model

import src.exception.FailedToReadObject
import src.game.entity.Entity
import src.game.entity.parts.position.{Direction, Position}
import src.game.entity.parts.state.State
import src.game.temporal.Timestamp

import scala.util.{Failure, Try}
import scala.xml.{Node, NodeSeq}

final case class EntityEntry(id: String,
                             name: String,
                             creationTimestamp: Long,
                             state: Option[State],
                             stateTimestamp: Option[Long],
                             x: Option[Int],
                             y: Option[Int],
                             direction: Option[Direction],
                             positionTimestamp: Option[Long]):

    def position: Option[Position] = for {
        x <- x
        y <- y
    } yield {
        Position(x, y)
    }

    def toXml: Node =
            <Entity>
                <id> {id} </id>
                <name> {name} </name>
                <creationTimestamp> {creationTimestamp} </creationTimestamp>
                {state.fold(NodeSeq.Empty) { state => <state> {state} </state> }}
                {stateTimestamp.fold(NodeSeq.Empty) { stateTimestamp => <stateTimestamp> {stateTimestamp} </stateTimestamp> }}
                {x.fold(NodeSeq.Empty) { x => <x> {x} </x> }}
                {y.fold(NodeSeq.Empty) { y => <y> {y} </y> }}
                {direction.fold(NodeSeq.Empty) { direction => <direction> {direction} </direction> }}
                {positionTimestamp.fold(NodeSeq.Empty) { positionTimestamp => <positionTimestamp> {positionTimestamp} </positionTimestamp> }}
            </Entity>

object EntityEntry:

    def fromXml(xml: Node): Try[EntityEntry] =
        val id = Try((xml \ "id").map(_.text.trim).head)
        val name = Try((xml \ "name").map(_.text.trim).head)
        val creationTimestamp = Try((xml \ "creationTimestamp").map(_.text.trim).map(_.toLong).head)
        val state = Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
        val stateTimestamp = Try((xml \ "stateTimestamp").map(_.text.trim).map(_.toLong).headOption)
        val x = Try((xml \ "x").map(_.text.trim).map(_.toInt).headOption)
        val y = Try((xml \ "y").map(_.text.trim).map(_.toInt).headOption)
        val direction = Try((xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption)
        val positionTimestamp = Try((xml \ "positionTimestamp").map(_.text.trim).map(_.toLong).headOption)

        {
            for
                id <- id
                name <- name
                creationTimestamp <- creationTimestamp
                state <- state
                stateTimestamp <- stateTimestamp
                x <- x
                y <- y
                direction <- direction
                positionTimestamp <- positionTimestamp
            yield
                EntityEntry(id, name, creationTimestamp, state, stateTimestamp, x, y, direction, positionTimestamp)
        }.recoverWith {
            case e => Failure(new FailedToReadObject("EntityEntry", e.getMessage))
        }
