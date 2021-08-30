package dod.data.model

import dod.exception.FailedToReadObject
import dod.game.gameobject.parts.position.Direction
import dod.game.gameobject.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class AnimationSelectorVariantEntity(state: Option[State], direction: Option[Direction], animationId: Int)

object AnimationSelectorVariantEntity:

    def fromXML(xml: Node): Try[AnimationSelectorVariantEntity] = {
        for
            state <- Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
            direction <- Try((xml \ "direction").map(_.text.trim).map(Direction.valueOf).headOption)
            animationId <- Try((xml \ "animationId").map(_.text.trim).map(_.toInt).head)
        yield
            AnimationSelectorVariantEntity(state, direction, animationId)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("AnimationSelectorVariantEntity", e.getMessage))
    }
