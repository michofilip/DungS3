package src.game.service.serialization

import src.data.model.EntityEntry
import src.exception.FailedToReadObject
import src.game.event.Event
import src.game.event.Event.{Despawn, MoveBy, MoveTo, Spawn, StartTimer, StopTimer}
import src.utils.TryUtils.*

import java.util.UUID
import scala.util.{Failure, Try}
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

        case Despawn(entityIds) =>
            <Event name="Despawn" >
                <entityIds>
                    {entityIds.map(entityId => {<entityId> {entityId} </entityId>})}
                </entityIds>
            </Event>

        case Spawn(useCurrentTimestamp, entities, events) =>
            <Event name="Spawn" >
                <useCurrentTimestamp> {useCurrentTimestamp} </useCurrentTimestamp>
                <entities> {entities.map(_.toXml)} </entities>
                <events> {events.map(EventSerializationService.toXml)} </events>
            </Event>
    }

    // TODO fix it
    def fromXml(xml: Node): Try[Event] =
        (xml \ "@name").text.trim match {
            case "MoveBy" => Try {
                MoveBy(
                    entityId = UUID.fromString((xml \ "entityId").text.trim),
                    dx = (xml \ "dx").text.trim.toInt,
                    dy = (xml \ "dy").text.trim.toInt
                )
            }

            case "MoveTo" => Try {
                MoveTo(
                    entityId = UUID.fromString((xml \ "entityId").text.trim),
                    x = (xml \ "x").text.trim.toInt,
                    y = (xml \ "y").text.trim.toInt
                )
            }

            case "StartTimer" => Try {
                StopTimer
            }

            case "StopTimer" => Try {
                StopTimer
            }

            case "Despawn" => Try {
                Despawn(
                    entityIds = (xml \ "entityIds" \ "entityId").map(_.text.trim).map(UUID.fromString)
                )
            }

            case "Spawn" => Try {
                Spawn(
                    useCurrentTimestamp = (xml \ "useCurrentTimestamp").text.trim.toBoolean,
                    entities = (xml \ "entities" \ "Entity").map(EntityEntry.fromXml).invertTry.get,
                    events = (xml \ "events" \ "Event").map(EventSerializationService.fromXml).invertTry.get
                )
            }

            case _ => Failure(new FailedToReadObject("Event", "unknown event"))
        }
