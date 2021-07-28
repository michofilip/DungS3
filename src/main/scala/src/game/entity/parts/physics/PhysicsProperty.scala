package src.game.entity.parts.physics

import src.game.entity.parts.physics.PhysicsProperty
import src.game.entity.selector.PhysicsSelector

class PhysicsProperty private(val physicsSelector: Option[PhysicsSelector])

object PhysicsProperty:

    lazy val empty: PhysicsProperty =
        new PhysicsProperty(physicsSelector = None)

    def apply(physicsSelector: PhysicsSelector): PhysicsProperty =
        new PhysicsProperty(physicsSelector = Some(physicsSelector))
