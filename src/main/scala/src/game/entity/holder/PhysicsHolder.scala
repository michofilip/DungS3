package src.game.entity.holder

import src.game.entity.Entity
import src.game.entity.parts.physics.{Physics, PhysicsProperty, PhysicsSelector}

trait PhysicsHolder[T <: Entity]:
    this: StateHolder[T] =>

    protected val physicsProperty: PhysicsProperty

    def hasPhysics: Boolean = physicsProperty.hasPhysics

    def physics: Option[Physics] = physicsProperty.physics(state)
