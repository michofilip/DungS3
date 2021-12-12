package dod.game.gameobject.parts.graphics

import dod.game.gameobject.parts.graphics.GraphicsProperty
import dod.game.gameobject.parts.position.Direction
import dod.game.gameobject.parts.state.State
import dod.game.temporal.Duration

final class GraphicsProperty(val layer: Int,
                             animationSelector: AnimationSelector) {

    def frame(state: Option[State], direction: Option[Direction], duration: Duration): Option[Frame] =
        animationSelector.selectAnimation(state, direction).map { animation =>
            animation.frame(duration)
        }
}
