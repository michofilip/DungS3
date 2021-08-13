package src.data.model

import src.game.entity.parts.state.State

import scala.util.Try
import scala.xml.Node

final case class EntityPrototypeEntry(name: String,
                                      availableStates: Seq[State],
                                      hasPosition: Boolean,
                                      hasDirection: Boolean,
                                      physicsSelectorId: Option[Int],
                                      layer: Option[Int],
                                      animationSelectorId: Option[Int])

object EntityPrototypeEntry:

    def fromXML(xml: Node): Option[EntityPrototypeEntry] = Try {
        val name = (xml \ "name").text.trim
        val availableStates = (xml \ "availableStates" \ "state").map(_.text.trim).map(State.valueOf)
        val hasPosition = (xml \ "hasPosition").text.trim.toBoolean
        val hasDirection = (xml \ "hasDirection").text.trim.toBoolean
        val physicsSelectorId = (xml \ "physicsSelectorId").map(_.text.trim.toInt).headOption
        val layer = (xml \ "layer").map(_.text.trim.toInt).headOption
        val animationSelectorId = (xml \ "animationSelectorId").map(_.text.trim.toInt).headOption

        EntityPrototypeEntry(name, availableStates, hasPosition, hasDirection, physicsSelectorId, layer, animationSelectorId)
    }.toOption
