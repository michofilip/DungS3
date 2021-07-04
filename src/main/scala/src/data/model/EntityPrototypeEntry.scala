package src.data.model

import src.data.file.FileReader.{Reader, *}
import src.game.entity.parts.State

import scala.util.Try
import scala.xml.Node

case class EntityPrototypeEntry(name: String,
                                availableStates: Seq[State],
                                hasPosition: Boolean,
                                hasDirection: Boolean,
                                physicsSelectorId: Option[Int],
                                animationSelectorId: Option[Int])

object EntityPrototypeEntry:

    val reader: Reader[EntityPrototypeEntry] = strArr => Try {
        val name = strArr(0)
        val availableStates = strArr(1).asSeq(State.valueOf)
        val hasPosition = strArr(2).asBoolean
        val hasDirection = strArr(3).asBoolean
        val physicsSelectorId = strArr(4).asOption(_.asInt)
        val animationSelectorId = strArr(5).asOption(_.asInt)

        EntityPrototypeEntry(
            name = name,
            availableStates = availableStates,
            hasPosition = hasPosition,
            hasDirection = hasDirection,
            physicsSelectorId = physicsSelectorId,
            animationSelectorId = animationSelectorId
        )
    }.toOption

    def fromXML(xml: Node): Option[EntityPrototypeEntry] = Try {
        val name = (xml \ "name").text.trim
        val availableStates = (xml \ "availableStates" \ "state").map(_.text.trim).map(State.valueOf)
        val hasPosition = (xml \ "hasPosition").text.trim.toBoolean
        val hasDirection = (xml \ "hasDirection").text.trim.toBoolean
        val physicsSelectorId = (xml \ "physicsSelectorId").map(_.text.trim.toInt).headOption
        val animationSelectorId = (xml \ "animationSelectorId").map(_.text.trim.toInt).headOption

        EntityPrototypeEntry(name, availableStates, hasPosition, hasDirection, physicsSelectorId, animationSelectorId)
    }.toOption
