package dod.game.gameobject.parts.physics

import dod.game.gameobject.parts.physics.Physics
import dod.game.gameobject.parts.state.State

final class PhysicsSelector private(physicsMap: Map[Option[State], Physics]):
    def selectPhysics(state: Option[State]): Option[Physics] = physicsMap.get(state)

object PhysicsSelector:
    def apply(physics: Seq[(Option[State], Physics)]): PhysicsSelector =
        new PhysicsSelector(physics.toMap)
        