package src.data.model

import src.game.entity.Entity
import src.game.entity.parts.position.{Direction, Position}
import src.game.entity.parts.state.State
import src.game.temporal.Timestamp

import scala.util.Try
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

    def fromXml(xml: Node): Option[EntityEntry] = Try {
        val id = (xml \ "id").text.trim
        val name = (xml \ "name").text.trim
        val creationTimestamp = (xml \ "creationTimestamp").text.trim.toLong
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val stateTimestamp = (xml \ "stateTimestamp").map(_.text.trim.toLong).headOption
        val x = (xml \ "x").map(_.text.trim.toInt).headOption
        val y = (xml \ "y").map(_.text.trim.toInt).headOption
        val direction = (xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption
        val positionTimestamp = (xml \ "positionTimestamp").map(_.text.trim.toLong).headOption

        EntityEntry(id, name, creationTimestamp, state, stateTimestamp, x, y, direction, positionTimestamp)
    }.toOption
