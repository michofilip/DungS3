package src.game.entity

import src.game.entity.EntityPrototype.{defaultDirection, defaultPosition}
import src.game.entity.parts.graphics.{AnimationSelector, GraphicsProperty}
import src.game.entity.parts.physics.{PhysicsProperty, PhysicsSelector}
import src.game.entity.parts.position.{Direction, Position, PositionProperty}
import src.game.entity.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

final class EntityPrototype(private val name: String,
                            private val availableStates: Seq[State],
                            private val hasPosition: Boolean,
                            private val hasDirection: Boolean,
                            private val physicsSelector: Option[PhysicsSelector],
                            private val animationSelector: Option[AnimationSelector]):

    def getStateProperty(state: Option[State], stateTimestamp: Option[Timestamp]): StateProperty =
        if availableStates.isEmpty then
            StateProperty.empty
        else state match {
            case Some(state) if availableStates.contains(state) => StateProperty(state, stateTimestamp.getOrElse(Timestamp.zero))
            case _ => StateProperty(availableStates.head, stateTimestamp.getOrElse(Timestamp.zero))
        }

    def getPositionProperty(position: Option[Position], direction: Option[Direction], positionTimestamp: Option[Timestamp]): PositionProperty =
        if !hasPosition then
            PositionProperty.empty
        else if hasDirection then
            PositionProperty(position = position.getOrElse(defaultPosition), direction = direction.getOrElse(defaultDirection), timestamp = positionTimestamp.getOrElse(Timestamp.zero))
        else
            PositionProperty(position = position.getOrElse(defaultPosition), timestamp = positionTimestamp.getOrElse(Timestamp.zero))

    def getPhysicsProperty: PhysicsProperty =
        physicsSelector.fold(PhysicsProperty.empty)(PhysicsProperty.apply)

    def getGraphicsProperty: GraphicsProperty =
        animationSelector.fold(GraphicsProperty.empty)(GraphicsProperty.apply)


object EntityPrototype:
    val defaultPosition = Position(0, 0)
    val defaultDirection = Direction.North
