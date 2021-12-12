package dod.game.gameobject.parts.physics

import dod.game.gameobject.parts.physics.PhysicsProperty
import dod.game.gameobject.parts.state.State

final class PhysicsProperty(physicsSelector: PhysicsSelector) {

    def physics(state: Option[State]): Option[Physics] =
        physicsSelector.selectPhysics(state)
}
