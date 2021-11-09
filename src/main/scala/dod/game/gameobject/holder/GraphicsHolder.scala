package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.parts.graphics.{AnimationSelector, Frame, GraphicsProperty}
import dod.game.temporal.{Duration, Timestamp}

trait GraphicsHolder[T <: GameObject]:
    this: CommonsHolder with StateHolder[T] with PositionHolder[T] =>

    protected val graphicsProperty: GraphicsProperty

    def hasGraphics: Boolean = graphicsProperty.hasGraphics

    def layer: Option[Int] = graphicsProperty.layer

    def frame(timestamp: Timestamp): Option[Frame] =
        val animationTimestamp = stateTimestamp.getOrElse(creationTimestamp)
        val animationDuration = Duration.durationBetween(animationTimestamp, timestamp)

        graphicsProperty.frame(state, direction, animationDuration)
        