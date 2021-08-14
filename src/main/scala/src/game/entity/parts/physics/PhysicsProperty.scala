package src.game.entity.parts.physics

import src.game.entity.parts.physics.PhysicsProperty
import src.game.entity.parts.state.State

sealed abstract class PhysicsProperty:
    def hasPhysics: Boolean

    def physics(state: Option[State]): Option[Physics]

object PhysicsProperty:

    final class EmptyPhysicsProperty private[PhysicsProperty]() extends PhysicsProperty :
        override def hasPhysics: Boolean = false

        override def physics(state: Option[State]): Option[Physics] = None


    final class SelectorPhysicsProperty private[PhysicsProperty](physicsSelector: PhysicsSelector) extends PhysicsProperty :
        override def hasPhysics: Boolean = true

        override def physics(state: Option[State]): Option[Physics] =
            physicsSelector.selectPhysics(state)


    val empty: PhysicsProperty =
        new EmptyPhysicsProperty()

    def apply(physicsSelector: PhysicsSelector): PhysicsProperty =
        new SelectorPhysicsProperty(physicsSelector)
