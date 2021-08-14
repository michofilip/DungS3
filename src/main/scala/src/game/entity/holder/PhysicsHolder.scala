package src.game.entity.holder

import src.game.entity.Entity
import src.game.entity.parts.physics.{Physics, PhysicsProperty, PhysicsSelector}

trait PhysicsHolder[T <: Entity]:
    this: StateHolder[T] =>

    protected val physicsProperty: PhysicsProperty

    def physics: Option[Physics] =
        physicsProperty.physicsSelector.flatMap { physicsSelector =>
            physicsSelector.selectPhysics(state)
        }

    def hasPhysics: Boolean = physicsProperty.physicsSelector.isDefined
