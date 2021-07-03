package src.data.model

import src.data.file.FileReader.Reader
import src.game.entity.parts.{Direction, State}

import scala.util.Try

case class AnimationSelectorEntry(id: Int, state: Option[State], direction: Option[Direction], animationId: Int)

object AnimationSelectorEntry:

    def reader: Reader[AnimationSelectorEntry] = strArr => Try {
        val id = strArr(0).toInt
        val state = if strArr(1).nonEmpty then Some(State.valueOf(strArr(1))) else None
        val direction = if strArr(2).nonEmpty then Some(Direction.valueOf(strArr(2))) else None
        val animationId = strArr(3).toInt

        AnimationSelectorEntry(id = id, state = state, direction = direction, animationId = animationId)
    }.toOption
