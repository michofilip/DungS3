package dod.game.gameobject.parts.graphics

import dod.game.gameobject.parts.graphics.Animation
import dod.game.gameobject.parts.position.Direction
import dod.game.gameobject.parts.state.State

final class AnimationSelector(animationMap: Map[(Option[State], Option[Direction]), Animation]):
    def selectAnimation(state: Option[State], direction: Option[Direction]): Option[Animation] = animationMap.get(state, direction)

object AnimationSelector:
    def apply(animations: Seq[((Option[State], Option[Direction]), Animation)]): AnimationSelector =
        new AnimationSelector(animations.toMap)
