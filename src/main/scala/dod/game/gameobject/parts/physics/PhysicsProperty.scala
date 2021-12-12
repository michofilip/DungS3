package dod.game.gameobject.parts.physics

import dod.game.gameobject.parts.physics.PhysicsProperty
import dod.game.gameobject.parts.state.State

class PhysicsProperty(physicsSelector: PhysicsSelector):

    def physics(state: Option[State]): Option[Physics] =
        physicsSelector.selectPhysics(state)
