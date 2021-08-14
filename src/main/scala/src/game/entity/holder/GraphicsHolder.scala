package src.game.entity.holder

import src.game.entity.Entity
import src.game.entity.parts.graphics.{AnimationSelector, Frame, GraphicsProperty}
import src.game.temporal.{Duration, Timestamp}

trait GraphicsHolder[T <: Entity]:
    this: CommonsHolder with StateHolder[T] with PositionHolder[T] =>

    protected val graphicsProperty: GraphicsProperty

    def hasGraphics: Boolean = graphicsProperty.hasGraphics

    def layer: Option[Int] = graphicsProperty.layer

    def frame(timestamp: Timestamp): Option[Frame] =
        val animationTimestamp = stateTimestamp.getOrElse(creationTimestamp)
        val animationDuration = Duration.durationBetween(animationTimestamp, timestamp)

        graphicsProperty.frame(state, direction, animationDuration)
        