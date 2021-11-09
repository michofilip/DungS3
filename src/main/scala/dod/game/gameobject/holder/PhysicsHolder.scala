package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.parts.physics.{Physics, PhysicsProperty, PhysicsSelector}

trait PhysicsHolder[T <: GameObject]:
    this: StateHolder[T] =>

    protected val physicsProperty: PhysicsProperty

    def hasPhysics: Boolean = physicsProperty.hasPhysics

    def physics: Option[Physics] = physicsProperty.physics(state)
