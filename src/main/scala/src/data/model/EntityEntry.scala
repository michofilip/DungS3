package src.data.model

import src.game.entity.parts.{Direction, State}
import src.game.temporal.Timestamp

import scala.util.Try
import scala.xml.{Node, NodeSeq}

case class EntityEntry(id: String, name: String, timestamp: Long, state: Option[State], x: Option[Int], y: Option[Int], direction: Option[Direction]):

    def toXml: Node =
        <Entity>
            <id> {id} </id>
            <name> {name} </name>
            <timestamp> {timestamp} </timestamp>
            {state.fold(NodeSeq.Empty) { state => <state> {state} </state> }}
            {x.fold(NodeSeq.Empty) { x => <x> {x} </x> }}
            {y.fold(NodeSeq.Empty) { y => <y> {y} </y> }}
            {direction.fold(NodeSeq.Empty) { direction => <direction> {direction} </direction> }}
        </Entity>

object EntityEntry:

    def fromXML(xml: Node): Option[EntityEntry] = Try {
        val id = (xml \ "id").text.trim
        val name = (xml \ "name").text.trim
        val timestamp = (xml \ "timestamp").text.trim.toLong
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val x = (xml \ "x").map(_.text.trim.toInt).headOption
        val y = (xml \ "y").map(_.text.trim.toInt).headOption
        val direction = (xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption

        EntityEntry(id, name, timestamp, state, x, y, direction)
    }.toOption
