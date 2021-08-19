package src.data.model

import src.exception.FailedToReadObject
import src.game.entity.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class EntityPrototypeEntry(name: String,
                                      availableStates: Seq[State],
                                      hasPosition: Boolean,
                                      hasDirection: Boolean,
                                      physicsSelectorId: Option[Int],
                                      layer: Option[Int],
                                      animationSelectorId: Option[Int])

object EntityPrototypeEntry:

    def fromXML(xml: Node): Try[EntityPrototypeEntry] = {
        for
            name <- Try((xml \ "name").map(_.text.trim).head)
            availableStates <- Try((xml \ "availableStates" \ "state").map(_.text.trim).map(State.valueOf))
            hasPosition <- Try((xml \ "hasPosition").map(_.text.trim).map(_.toBoolean).head)
            hasDirection <- Try((xml \ "hasDirection").map(_.text.trim).map(_.toBoolean).head)
            physicsSelectorId <- Try((xml \ "physicsSelectorId").map(_.text.trim).map(_.toInt).headOption)
            layer <- Try((xml \ "layer").map(_.text.trim).map(_.toInt).headOption)
            animationSelectorId <- Try((xml \ "animationSelectorId").map(_.text.trim).map(_.toInt).headOption)
        yield
            EntityPrototypeEntry(name, availableStates, hasPosition, hasDirection, physicsSelectorId, layer, animationSelectorId)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("EntityPrototypeEntry", e.getMessage))
    }
