package src.game.entity.selector

import src.game.entity.parts.animation.Animation
import src.game.entity.parts.{Direction, State}

final class AnimationSelector(animationMap: Map[(Option[State], Option[Direction]), Animation]):
    def selectAnimation(state: Option[State], direction: Option[Direction]): Option[Animation] = animationMap.get(state, direction)

object AnimationSelector:
    def apply(animations: Seq[((Option[State], Option[Direction]), Animation)]): AnimationSelector =
        new AnimationSelector(animations.toMap)

    val empty: AnimationSelector = AnimationSelector(Seq.empty)
