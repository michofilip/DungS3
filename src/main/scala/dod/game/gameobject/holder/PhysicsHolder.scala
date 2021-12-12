package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.parts.physics.{Physics, PhysicsProperty, PhysicsSelector}

trait PhysicsHolder[T <: GameObject] {
    this: StateHolder[T] =>

    protected val physicsProperty: Option[PhysicsProperty]

    def hasPhysics: Boolean = physicsProperty.isDefined

    def physics: Option[Physics] = physicsProperty.flatMap(_.physics(state))
}
