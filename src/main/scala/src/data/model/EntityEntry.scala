package src.data.model

import src.data.file.FileReader.{Reader, *}
import src.data.file.FileWriter.Writer
import src.game.entity.parts.{Direction, State}
import src.game.temporal.Timestamp

import scala.util.Try
import scala.xml.{Node, NodeSeq}

case class EntityEntry(id: String, name: String, timestamp: Long, state: Option[State], x: Option[Int], y: Option[Int], direction: Option[Direction]):

    def xml: Node =
        <Entity>
            <id>
                {id}
            </id>
            <name>
                {name}
            </name>
            <timestamp>
                {timestamp}
            </timestamp>{state.fold(NodeSeq.Empty) { state =>
            <state>
                {state}
            </state>
        }}{x.fold(NodeSeq.Empty) { x =>
            <x>
                {x}
            </x>
        }}{y.fold(NodeSeq.Empty) { y =>
            <y>
                {y}
            </y>
        }}{direction.fold(NodeSeq.Empty) { direction =>
            <direction>
                {direction}
            </direction>
        }}
        </Entity>

object EntityEntry:

    val reader: Reader[EntityEntry] = strArr => Try {
        val id = strArr(0)
        val name = strArr(1)
        val timestamp = strArr(2).toLong
        val state = strArr(3).asOption(State.valueOf)
        val x = strArr(4).asOption(_.asInt)
        val y = strArr(5).asOption(_.asInt)
        val direction = strArr(6).asOption(Direction.valueOf)

        EntityEntry(id = id, name = name, timestamp = timestamp, state = state, x = x, y = y, direction = direction)
    }.toOption

    val writer: Writer[EntityEntry] = entry => Array(
        entry.id,
        entry.name,
        entry.timestamp.toString,
        entry.state.map(_.toString).getOrElse(""),
        entry.x.map(_.toString).getOrElse(""),
        entry.y.map(_.toString).getOrElse(""),
        entry.direction.map(_.toString).getOrElse("")
    )

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
