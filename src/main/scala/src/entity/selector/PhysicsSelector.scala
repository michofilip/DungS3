package src.entity.selector

import src.entity.parts.{Physics, State}

final class PhysicsSelector private(physicsMap: Map[Option[State], Physics]):
    def selectPhysics(state: Option[State]): Option[Physics] = physicsMap.get(state)

object PhysicsSelector:
    def apply(physics: (Option[State], Physics)*): PhysicsSelector =
        new PhysicsSelector(physics.toMap)

    val empty: PhysicsSelector = PhysicsSelector()