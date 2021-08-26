package src.game.gameobject.holder

import src.game.gameobject.Entity
import src.game.gameobject.parts.physics.{Physics, PhysicsProperty, PhysicsSelector}

trait PhysicsHolder[T <: Entity]:
    this: StateHolder[T] =>

    protected val physicsProperty: PhysicsProperty

    def hasPhysics: Boolean = physicsProperty.hasPhysics

    def physics: Option[Physics] = physicsProperty.physics(state)
