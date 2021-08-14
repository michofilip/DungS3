package src.game.entity.parts.graphics

import src.game.entity.parts.graphics.GraphicsProperty
import src.game.entity.parts.position.Direction
import src.game.entity.parts.state.State
import src.game.temporal.Duration

sealed abstract class GraphicsProperty:
    def hasGraphics: Boolean

    def layer: Option[Int]

    def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame]

object GraphicsProperty:

    final class EmptyGraphicsProperty private[GraphicsProperty]() extends GraphicsProperty :
        override def hasGraphics: Boolean = false

        override def layer: Option[Int] = None

        override def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame] = None


    final class SelectorGraphicsProperty private[GraphicsProperty](_layer: Int, animationSelector: AnimationSelector) extends GraphicsProperty :
        override def hasGraphics: Boolean = true

        override def layer: Option[Int] = Some(_layer)

        override def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame] =
            animationSelector.selectAnimation(state, direction).map { animation =>
                animation.frame(duration)
            }


    lazy val empty: GraphicsProperty =
        new EmptyGraphicsProperty()

    def apply(layer: Int, animationSelector: AnimationSelector): GraphicsProperty =
        new SelectorGraphicsProperty(layer, animationSelector)
