package src.game.service

import src.game.event.Event
import src.game.event.Event.*

import java.util.UUID
import scala.util.Try
import scala.xml.Node

object EventSerializationService:

    def toXml(event: Event): Node = event match {
        case MoveBy(entityId, dx, dy) =>
            <Event name="MoveBy">
                <entityId> {entityId} </entityId>
                <dx> {dx} </dx>
                <dy> {dy} </dy>
            </Event>

        case MoveTo(entityId, x, y) =>
            <Event name="MoveTo">
                <entityId> {entityId} </entityId>
                <x> {x} </x>
                <y> {y} </y>
            </Event>

        case StartTimer =>
                <Event name="StartTimer" />

        case StopTimer =>
                <Event name="StopTimer" />

        //        case _ =>
    }

    def fromXml(xml: Node): Option[Event] = Try {
        (xml \ "@name").text.trim match {
            case "MoveBy" => MoveBy(
                entityId = UUID.fromString((xml \ "entityId").text.trim),
                dx = (xml \ "dx").text.trim.toInt,
                dy = (xml \ "dy").text.trim.toInt
            )

            case "MoveTo" => MoveTo(
                entityId = UUID.fromString((xml \ "entityId").text.trim),
                x = (xml \ "x").text.trim.toInt,
                y = (xml \ "y").text.trim.toInt
            )

            case "StartTimer" => StopTimer

            case "StopTimer" => StopTimer

        }
    }.toOption
