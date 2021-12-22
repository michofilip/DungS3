package dod.data.model

import dod.exception.FailedToReadObject
import dod.game.gameobject.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class GameObjectPrototypeEntity(name: String,
                                           availableStates: Seq[State],
                                           hasPosition: Boolean,
                                           physicsSelectorId: Option[Int],
                                           layer: Option[Int],
                                           animationSelectorId: Option[Int])

object GameObjectPrototypeEntity:

    def fromXML(xml: Node): Try[GameObjectPrototypeEntity] = {
        for
            name <- Try((xml \ "name").map(_.text.trim).head)
            availableStates <- Try((xml \ "availableStates" \ "state").map(_.text.trim).map(State.valueOf))
            hasPosition <- Try((xml \ "hasPosition").map(_.text.trim).map(_.toBoolean).head)
            physicsSelectorId <- Try((xml \ "physicsSelectorId").map(_.text.trim).map(_.toInt).headOption)
            layer <- Try((xml \ "layer").map(_.text.trim).map(_.toInt).headOption)
            animationSelectorId <- Try((xml \ "animationSelectorId").map(_.text.trim).map(_.toInt).headOption)
        yield
            GameObjectPrototypeEntity(name, availableStates, hasPosition, physicsSelectorId, layer, animationSelectorId)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("GameObjectPrototypeEntity", e.getMessage))
    }
