package src.data.model

import src.game.entity.parts.position.Direction
import src.game.entity.parts.state.State

import scala.util.Try
import scala.xml.Node

final case class AnimationSelectorVariantEntry(state: Option[State], direction: Option[Direction], animationId: Int)

object AnimationSelectorVariantEntry:

    def fromXML(xml: Node): Try[AnimationSelectorVariantEntry] =
        val state = Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
        val direction = Try((xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption)
        val animationId = Try((xml \ "animationId").map(_.text.trim).map(_.toInt).head)

        for {
            state <- state
            direction <- direction
            animationId <- animationId
        } yield {
            AnimationSelectorVariantEntry(state, direction, animationId)
        }
