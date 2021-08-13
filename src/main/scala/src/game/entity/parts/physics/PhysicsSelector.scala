package src.game.entity.parts.physics

import src.game.entity.parts.physics.Physics
import src.game.entity.parts.state.State

final class PhysicsSelector private(physicsMap: Map[Option[State], Physics]):
    def selectPhysics(state: Option[State]): Option[Physics] = physicsMap.get(state)

object PhysicsSelector:
    def apply(physics: Seq[(Option[State], Physics)]): PhysicsSelector =
        new PhysicsSelector(physics.toMap)
        