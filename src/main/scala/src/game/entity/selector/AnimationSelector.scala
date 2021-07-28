package src.game.entity.selector

import src.game.entity.parts.graphics.Animation
import src.game.entity.parts.position.Direction
import src.game.entity.parts.state.State

final class AnimationSelector(animationMap: Map[(Option[State], Option[Direction]), Animation]):
    def selectAnimation(state: Option[State], direction: Option[Direction]): Option[Animation] = animationMap.get(state, direction)

object AnimationSelector:
    def apply(animations: Seq[((Option[State], Option[Direction]), Animation)]): AnimationSelector =
        new AnimationSelector(animations.toMap)
        