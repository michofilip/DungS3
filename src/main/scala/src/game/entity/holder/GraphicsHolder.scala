package src.game.entity.holder

import src.game.entity.Entity
import src.game.entity.parts.graphics.{AnimationSelector, Frame, GraphicsProperty}
import src.game.temporal.{Duration, Timestamp}

trait GraphicsHolder[T <: Entity]:
    this: CommonsHolder with StateHolder[T] with PositionHolder[T] =>

    protected val graphicsProperty: GraphicsProperty

    def layer: Option[Int] = graphicsProperty.layer

    def frame(timestamp: Timestamp): Option[Frame] =
        for {
            animationSelector <- graphicsProperty.animationSelector
            animation <- animationSelector.selectAnimation(state, direction)
        } yield {
            val animationTimestamp = stateTimestamp.getOrElse(creationTimestamp)

            animation.frame(Duration.durationBetween(animationTimestamp, timestamp))
        }

    def hasGraphics: Boolean = graphicsProperty.animationSelector.isDefined


