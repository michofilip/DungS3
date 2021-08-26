package src.game.service.serialization

import src.data.model.GameObjectEntity
import src.exception.FailedToReadObject
import src.game.event.Event
import src.game.event.Event.{Despawn, MoveBy, MoveTo, Spawn, StartTimer, StopTimer}
import src.utils.TryUtils.*

import java.util.UUID
import scala.util.{Failure, Success, Try}
import scala.xml.Node

object EventSerializationService:

    def toXml(event: Event): Node = event match
        case MoveBy(gameObjectId, dx, dy) =>
            <Event name="MoveBy">
                <gameObjectId> {gameObjectId} </gameObjectId>
                <dx> {dx} </dx>
                <dy> {dy} </dy>
            </Event>

        case MoveTo(gameObjectId, x, y) =>
            <Event name="MoveTo">
                <gameObjectId> {gameObjectId} </gameObjectId>
                <x> {x} </x>
                <y> {y} </y>
            </Event>

        case StartTimer =>
                <Event name="StartTimer" />

        case StopTimer =>
                <Event name="StopTimer" />

        case Despawn(gameObjectIds) =>
            <Event name="Despawn">
                <gameObjectIds>
                    {gameObjectIds.map(gameObjectId => {<gameObjectId> {gameObjectId} </gameObjectId>})}
                </gameObjectIds>
            </Event>

        case Spawn(useCurrentTimestamp, gameObjects, events) =>
            <Event name="Spawn">
                <useCurrentTimestamp> {useCurrentTimestamp} </useCurrentTimestamp>
                <gameObjects> {gameObjects.map(_.toXml)} </gameObjects>
                <events> {events.map(EventSerializationService.toXml)} </events>
            </Event>


    def fromXml(xml: Node): Try[Event] =
        (xml \ "@name").text.trim match
            case "MoveBy" =>
                for
                    gameObjectId <- Try((xml \ "gameObjectId").map(_.text.trim).map(UUID.fromString).head)
                    dx <- Try((xml \ "dx").map(_.text.trim).map(_.toInt).head)
                    dy <- Try((xml \ "dy").map(_.text.trim).map(_.toInt).head)
                yield
                    MoveBy(gameObjectId, dx, dy)

            case "MoveTo" =>
                for
                    gameObjectId <- Try((xml \ "gameObjectId").map(_.text.trim).map(UUID.fromString).head)
                    x <- Try((xml \ "x").map(_.text.trim).map(_.toInt).head)
                    y <- Try((xml \ "y").map(_.text.trim).map(_.toInt).head)
                yield
                    MoveTo(gameObjectId, x, y)

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
                    gameObjectIds <- Try((xml \ "gameObjectIds" \ "gameObjectId").map(_.text.trim).map(UUID.fromString))
                yield
                    Despawn(gameObjectIds)

            case "Spawn" =>
                for
                    useCurrentTimestamp <- Try((xml \ "useCurrentTimestamp").map(_.text.trim).map(_.toBoolean).head)
                    gameObjects <- (xml \ "gameObjects" \ "GameObject").map(GameObjectEntity.fromXml).toTrySeq
                    events <- (xml \ "events" \ "Event").map(EventSerializationService.fromXml).toTrySeq
                yield
                    Spawn(useCurrentTimestamp, gameObjects, events)

            case _ =>
                Failure(new FailedToReadObject("Event", "unknown event"))
