package src.data.model

import src.exception.FailedToReadObject
import src.game.entity.parts.position.Direction
import src.game.entity.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class AnimationSelectorVariantEntry(state: Option[State], direction: Option[Direction], animationId: Int)

object AnimationSelectorVariantEntry:

    def fromXML(xml: Node): Try[AnimationSelectorVariantEntry] = {
        for
            state <- Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
            direction <- Try((xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption)
            animationId <- Try((xml \ "animationId").map(_.text.trim).map(_.toInt).head)
        yield
            AnimationSelectorVariantEntry(state, direction, animationId)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("AnimationSelectorVariantEntry", e.getMessage))
    }
