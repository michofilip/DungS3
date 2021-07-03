package src.data.model

import src.data.file.FileReader.{Reader, *}
import src.game.entity.parts.{Direction, State}

import scala.util.Try

case class AnimationSelectorEntry(id: Int, state: Option[State], direction: Option[Direction], animationId: Int)

object AnimationSelectorEntry:

    def reader: Reader[AnimationSelectorEntry] = strArr => Try {
        val id = strArr(0).asInt
        val state = strArr(1).asOption(State.valueOf)
        val direction = strArr(2).asOption(Direction.valueOf)
        val animationId = strArr(3).asInt

        AnimationSelectorEntry(id = id, state = state, direction = direction, animationId = animationId)
    }.toOption
