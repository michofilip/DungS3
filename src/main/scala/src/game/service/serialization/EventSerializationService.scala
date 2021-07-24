package src.game.service.serialization

import src.data.model.EntityEntry
import src.game.event.Event
import src.game.event.Event.{Kill, MoveBy, MoveTo, Spawn, StartTimer, StopTimer}

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

        case Kill(entityId) =>
            <Event name="Kill" >
                <entityId> {entityId} </entityId>
            </Event>

        case Spawn(useCurrentTimestamp, entities, events) =>
            <Event name="Spawn" >
                <useCurrentTimestamp> {useCurrentTimestamp} </useCurrentTimestamp>
                <entities> {entities.map(_.toXml)} </entities>
                <events> {events.map(EventSerializationService.toXml)} </events>
            </Event>
    }

    def fromXml(xml: Node): Option[Event] =
        (xml \ "@name").text.trim match {
            case "MoveBy" => Try {
                MoveBy(
                    entityId = UUID.fromString((xml \ "entityId").text.trim),
                    dx = (xml \ "dx").text.trim.toInt,
                    dy = (xml \ "dy").text.trim.toInt
                )
            }.toOption

            case "MoveTo" => Try {
                MoveTo(
                    entityId = UUID.fromString((xml \ "entityId").text.trim),
                    x = (xml \ "x").text.trim.toInt,
                    y = (xml \ "y").text.trim.toInt
                )
            }.toOption

            case "StartTimer" => Try {
                StopTimer
            }.toOption

            case "StopTimer" => Try {
                StopTimer
            }.toOption

            case "Kill" => Try {
                Kill(
                    entityId = UUID.fromString((xml \ "entityId").text.trim)
                )
            }.toOption

            case "Spawn" => Try {
                Spawn(
                    useCurrentTimestamp = (xml \ "useCurrentTimestamp").text.trim.toBoolean,
                    entities = (xml \ "entities" \ "Entity").flatMap(EntityEntry.fromXml),
                    events = (xml \ "events" \ "Event").flatMap(EventSerializationService.fromXml)
                )
            }.toOption

            case _ => None
        }
