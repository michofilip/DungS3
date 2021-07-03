package src.data.model

import src.data.file.FileReader.Reader
import src.game.entity.parts.State

import scala.util.Try

case class EntityPrototypeEntry(name: String,
                                availableStates: Seq[State],
                                hasPosition: Boolean,
                                hasDirection: Boolean,
                                physicsSelectorId: Option[Int],
                                animationSelectorId: Option[Int])

object EntityPrototypeEntry:

    val reader: Reader[EntityPrototypeEntry] = strArr => Try {
        val name = strArr(0)
        val availableStates = strArr(1).split(',').map(_.trim).filter(_.nonEmpty).map(State.valueOf).toSeq
        val hasPosition = strArr(2) == "1"
        val hasDirection = strArr(3) == "1"
        val physicsSelectorId = if strArr(4).nonEmpty then Some(strArr(4).toInt) else None
        val animationSelectorId = if strArr(5).nonEmpty then Some(strArr(5).toInt) else None

        EntityPrototypeEntry(
            name = name,
            availableStates = availableStates,
            hasPosition = hasPosition,
            hasDirection = hasDirection,
            physicsSelectorId = physicsSelectorId,
            animationSelectorId = animationSelectorId
        )
    }.toOption
    