package src.game.entity.selector

import src.game.entity.parts.{Physics, State}

final class PhysicsSelector private(physicsMap: Map[Option[State], Physics]):
    def selectPhysics(state: Option[State]): Option[Physics] = physicsMap.get(state)

object PhysicsSelector:
    def apply(physics: Seq[(Option[State], Physics)]): PhysicsSelector =
        new PhysicsSelector(physics.toMap)

    val empty: PhysicsSelector = PhysicsSelector(Seq.empty)