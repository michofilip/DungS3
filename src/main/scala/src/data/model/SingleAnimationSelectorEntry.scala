package src.data.model

import src.game.entity.parts.{Direction, State}

import scala.util.Try
import scala.xml.Node

case class SingleAnimationSelectorEntry(state: Option[State], direction: Option[Direction], animationId: Int)

object SingleAnimationSelectorEntry:

    def fromXML(xml: Node): Option[SingleAnimationSelectorEntry] = Try {
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val direction = (xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption
        val animationId = (xml \ "animationId").text.trim.toInt

        SingleAnimationSelectorEntry(state, direction, animationId)
    }.toOption
