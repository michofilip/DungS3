package dod.game.gameobject.parts.graphics

import dod.game.gameobject.parts.graphics.GraphicsProperty
import dod.game.gameobject.parts.position.Direction
import dod.game.gameobject.parts.state.State
import dod.game.temporal.Duration

class GraphicsProperty(val layer: Int,
                       val animationSelector: AnimationSelector):
    //    def hasGraphics: Boolean
    //
    //    def layer: Option[Int]

    def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame] =
        animationSelector.selectAnimation(state, direction).map { animation =>
            animation.frame(duration)
        }

//object GraphicsProperty:
//
//    final class EmptyGraphicsProperty private[GraphicsProperty]() extends GraphicsProperty :
//        override def hasGraphics: Boolean = false
//
//        override def layer: Option[Int] = None
//
//        override def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame] = None
//
//
//    final class SelectorGraphicsProperty private[GraphicsProperty](_layer: Int, animationSelector: AnimationSelector) extends GraphicsProperty :
//        override def hasGraphics: Boolean = true
//
//        override def layer: Option[Int] = Some(_layer)
//
//        override def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame] =
//            animationSelector.selectAnimation(state, direction).map { animation =>
//                animation.frame(duration)
//            }
//
//
//    lazy val empty: GraphicsProperty =
//        new EmptyGraphicsProperty()
//
//    def apply(layer: Int, animationSelector: AnimationSelector): GraphicsProperty =
//        new SelectorGraphicsProperty(layer, animationSelector)
