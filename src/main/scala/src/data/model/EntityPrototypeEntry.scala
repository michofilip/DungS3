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

    def fromXML(xml: Node): Try[EntityPrototypeEntry] =
        val name = Try((xml \ "name").map(_.text.trim).head)
        val availableStates = Try((xml \ "availableStates" \ "state").map(_.text.trim).map(State.valueOf))
        val hasPosition = Try((xml \ "hasPosition").map(_.text.trim).map(_.toBoolean).head)
        val hasDirection = Try((xml \ "hasDirection").map(_.text.trim).map(_.toBoolean).head)
        val physicsSelectorId = Try((xml \ "physicsSelectorId").map(_.text.trim).map(_.toInt).headOption)
        val layer = Try((xml \ "layer").map(_.text.trim).map(_.toInt).headOption)
        val animationSelectorId = Try((xml \ "animationSelectorId").map(_.text.trim).map(_.toInt).headOption)

        {
            for
                name <- name
                availableStates <- availableStates
                hasPosition <- hasPosition
                hasDirection <- hasDirection
                physicsSelectorId <- physicsSelectorId
                layer <- layer
                animationSelectorId <- animationSelectorId
            yield
                EntityPrototypeEntry(name, availableStates, hasPosition, hasDirection, physicsSelectorId, layer, animationSelectorId)
        }.recoverWith {
            case e => Failure(new FailedToReadObject("EntityPrototypeEntry", e.getMessage))
        }
