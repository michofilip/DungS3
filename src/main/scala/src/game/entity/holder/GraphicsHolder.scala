package src.game.entity.holder

import src.game.entity.parts.graphics.{Frame, GraphicsProperty}
import src.game.entity.selector.AnimationSelector
import src.game.temporal.{Duration, Timestamp}

trait GraphicsHolder:
    this: CommonsHolder with StateHolder with PositionHolder =>

    protected val graphicsProperty: GraphicsProperty

    def frame(timestamp: Timestamp): Option[Frame] =
        for {
            animationSelector <- graphicsProperty.animationSelector
            animation <- animationSelector.selectAnimation(state, direction)
        } yield {
            val animationTimestamp = stateTimestamp.getOrElse(creationTimestamp)

            animation.frame(Duration.durationBetween(animationTimestamp, timestamp))
        }

    def hasGraphics: Boolean = graphicsProperty.animationSelector.isDefined


