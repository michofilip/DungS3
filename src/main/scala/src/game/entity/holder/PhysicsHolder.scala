package src.game.entity.holder

import src.game.entity.parts.physics.{Physics, PhysicsProperty, PhysicsSelector}

trait PhysicsHolder:
    this: StateHolder =>

    protected val physicsProperty: PhysicsProperty

    def physics: Option[Physics] =
        physicsProperty.physicsSelector.flatMap { physicsSelector =>
            physicsSelector.selectPhysics(state)
        }

    def hasPhysics: Boolean = physicsProperty.physicsSelector.isDefined
