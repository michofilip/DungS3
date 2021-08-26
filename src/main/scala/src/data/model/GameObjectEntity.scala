package src.data.model

import src.exception.FailedToReadObject
import src.game.gameobject.GameObject
import src.game.gameobject.parts.position.{Direction, Position}
import src.game.gameobject.parts.state.State
import src.game.temporal.Timestamp

import scala.util.{Failure, Try}
import scala.xml.{Node, NodeSeq}

final case class GameObjectEntity(id: String,
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
            <GameObject>
                <id> {id} </id>
                <name> {name} </name>
                <creationTimestamp> {creationTimestamp} </creationTimestamp>
                {state.fold(NodeSeq.Empty) { state => <state> {state} </state> }}
                {stateTimestamp.fold(NodeSeq.Empty) { stateTimestamp => <stateTimestamp> {stateTimestamp} </stateTimestamp> }}
                {x.fold(NodeSeq.Empty) { x => <x> {x} </x> }}
                {y.fold(NodeSeq.Empty) { y => <y> {y} </y> }}
                {direction.fold(NodeSeq.Empty) { direction => <direction> {direction} </direction> }}
                {positionTimestamp.fold(NodeSeq.Empty) { positionTimestamp => <positionTimestamp> {positionTimestamp} </positionTimestamp> }}
            </GameObject>

object GameObjectEntity:

    def fromXml(xml: Node): Try[GameObjectEntity] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).head)
            name <- Try((xml \ "name").map(_.text.trim).head)
            creationTimestamp <- Try((xml \ "creationTimestamp").map(_.text.trim).map(_.toLong).head)
            state <- Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
            stateTimestamp <- Try((xml \ "stateTimestamp").map(_.text.trim).map(_.toLong).headOption)
            x <- Try((xml \ "x").map(_.text.trim).map(_.toInt).headOption)
            y <- Try((xml \ "y").map(_.text.trim).map(_.toInt).headOption)
            direction <- Try((xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption)
            positionTimestamp <- Try((xml \ "positionTimestamp").map(_.text.trim).map(_.toLong).headOption)
        yield
            GameObjectEntity(id, name, creationTimestamp, state, stateTimestamp, x, y, direction, positionTimestamp)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("GameObject", e.getMessage))
    }
