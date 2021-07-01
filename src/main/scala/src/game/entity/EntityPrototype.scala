package src.game.entity

import src.game.entity.EntityPrototype.defaultPosition
import src.game.entity.parts.{Direction, Position, State}
import src.game.entity.selector.{AnimationSelector, PhysicsSelector}

final class EntityPrototype(private val name: String,
                            private val availableStates: Seq[State],
                            private val hasPosition: Boolean,
                            private val hasDirection: Boolean,
                            val physicsSelector: PhysicsSelector,
                            val animationSelector: AnimationSelector):

    def getValidatedState(state: Option[State]): Option[State] =
        state.filter(availableStates.contains).fold(availableStates.headOption)(_ => state)

    def getValidatedPosition(position: Option[Position]): Option[Position] =
        if (hasPosition && position.isDefined) position
        else if (hasPosition) Some(defaultPosition)
        else None

    def getValidatedDirection(direction: Option[Direction]): Option[Direction] =
        if (hasDirection && direction.isDefined) direction
        else if (hasDirection) Some(EntityPrototype.defaultDirection)
        else None

object EntityPrototype:
    val defaultPosition = Position(0, 0)
    val defaultDirection = Direction.North
