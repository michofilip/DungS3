package src.data.model

import src.game.entity.parts.{Direction, State}

import scala.util.Try
import scala.xml.Node

final case class AnimationSelectorVariantEntry(state: Option[State], direction: Option[Direction], animationId: Int)

object AnimationSelectorVariantEntry:

    def fromXML(xml: Node): Option[AnimationSelectorVariantEntry] = Try {
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val direction = (xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption
        val animationId = (xml \ "animationId").text.trim.toInt

        AnimationSelectorVariantEntry(state, direction, animationId)
    }.toOption
