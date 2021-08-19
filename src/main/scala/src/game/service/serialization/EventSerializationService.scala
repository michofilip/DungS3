package src.game.service.serialization

import src.data.model.EntityEntry
import src.exception.FailedToReadObject
import src.game.event.Event
import src.game.event.Event.{Despawn, MoveBy, MoveTo, Spawn, StartTimer, StopTimer}
import src.utils.TryUtils.*

import java.util.UUID
import scala.util.{Failure, Success, Try}
import scala.xml.Node

object EventSerializationService:

    def toXml(event: Event): Node = event match
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
            <Event name="Despawn">
                <entityIds>
                    {entityIds.map(entityId => {<entityId> {entityId} </entityId>})}
                </entityIds>
            </Event>

        case Spawn(useCurrentTimestamp, entities, events) =>
            <Event name="Spawn">
                <useCurrentTimestamp> {useCurrentTimestamp} </useCurrentTimestamp>
                <entities> {entities.map(_.toXml)} </entities>
                <events> {events.map(EventSerializationService.toXml)} </events>
            </Event>


    def fromXml(xml: Node): Try[Event] =
        (xml \ "@name").text.trim match
            case "MoveBy" =>
                for
                    entityId <- Try((xml \ "entityId").map(_.text.trim).map(UUID.fromString).head)
                    dx <- Try((xml \ "dx").map(_.text.trim).map(_.toInt).head)
                    dy <- Try((xml \ "dy").map(_.text.trim).map(_.toInt).head)
                yield
                    MoveBy(entityId, dx, dy)

            case "MoveTo" =>
                for
                    entityId <- Try((xml \ "entityId").map(_.text.trim).map(UUID.fromString).head)
                    x <- Try((xml \ "x").map(_.text.trim).map(_.toInt).head)
                    y <- Try((xml \ "y").map(_.text.trim).map(_.toInt).head)
                yield
                    MoveTo(entityId, x, y)

            case "StartTimer" =>
                Success {
                    StopTimer
                }

            case "StopTimer" =>
                Success {
                    StopTimer
                }

            case "Despawn" =>
                for
                    entityIds <- Try((xml \ "entityIds" \ "entityId").map(_.text.trim).map(UUID.fromString))
                yield
                    Despawn(entityIds)

            case "Spawn" =>
                for
                    useCurrentTimestamp <- Try((xml \ "useCurrentTimestamp").map(_.text.trim).map(_.toBoolean).head)
                    entities <- (xml \ "entities" \ "Entity").map(EntityEntry.fromXml).invertTry
                    events <- (xml \ "events" \ "Event").map(EventSerializationService.fromXml).invertTry
                yield
                    Spawn(useCurrentTimestamp, entities, events)

            case _ =>
                Failure(new FailedToReadObject("Event", "unknown event"))
