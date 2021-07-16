package src.game.service

import src.game.event.{Event, PositionEvent, TimeEvent}

import java.util.UUID
import scala.util.Try
import scala.xml.Node

object EventSerializationService:

    def toXml(event: Event): Node = event match {
        case PositionEvent.MoveBy(entityId, dx, dy) =>
            <Event name="MoveBy">
                <entityId> {entityId} </entityId>
                <dx> {dx} </dx>
                <dy> {dy} </dy>
            </Event>

        case PositionEvent.MoveTo(entityId, x, y) =>
            <Event name="MoveTo">
                <entityId> {entityId} </entityId>
                <x> {x} </x>
                <y> {y} </y>
            </Event>

        case TimeEvent.StartTimer =>
                <Event name="StartTimer" />

        case TimeEvent.StopTimer =>
                <Event name="StopTimer" />

        //        case _ =>
    }

    def fromXml(xml: Node): Option[Event] = Try {
        (xml \ "@name").text.trim match {
            case "MoveBy" =>
                PositionEvent.MoveBy(
                    entityId = UUID.fromString((xml \ "entityId").text.trim),
                    dx = (xml \ "dx").text.trim.toInt,
                    dy = (xml \ "dy").text.trim.toInt
                )

            case "MoveTo" =>
                PositionEvent.MoveTo(
                    entityId = UUID.fromString((xml \ "entityId").text.trim),
                    x = (xml \ "x").text.trim.toInt,
                    y = (xml \ "y").text.trim.toInt
                )

            case "StartTimer" =>
                TimeEvent.StopTimer

            case "StopTimer" =>
                TimeEvent.StopTimer

        }
    }.toOption
