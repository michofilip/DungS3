package dod.game.gameobject

import dod.game.gameobject.GameObjectPrototype.*
import dod.game.gameobject.parts.graphics.{AnimationSelector, GraphicsProperty}
import dod.game.gameobject.parts.physics.{PhysicsProperty, PhysicsSelector}
import dod.game.gameobject.parts.position.{Direction, Position, PositionProperty}
import dod.game.gameobject.parts.state.{State, StateProperty}
import dod.game.temporal.Timestamp

final class GameObjectPrototype(private val name: String,
                                private val availableStates: Seq[State],
                                private val hasPosition: Boolean,
                                private val hasDirection: Boolean,
                                private val layer: Option[Int],
                                private val physicsSelector: Option[PhysicsSelector],
                                private val animationSelector: Option[AnimationSelector]):

    def getStateProperty(state: Option[State], stateTimestamp: Option[Timestamp]): StateProperty =
        if availableStates.isEmpty then
            StateProperty.empty
        else state match
            case Some(state) if availableStates.contains(state) => StateProperty(state, stateTimestamp.getOrElse(defaultTimestamp))
            case _ => StateProperty(availableStates.head, stateTimestamp.getOrElse(defaultTimestamp))

    def getPositionProperty(position: Option[Position], direction: Option[Direction], positionTimestamp: Option[Timestamp]): PositionProperty =
        if !hasPosition then
            PositionProperty.empty
        else if hasDirection then
            PositionProperty(
                position = position.getOrElse(defaultPosition),
                direction = direction.getOrElse(defaultDirection),
                timestamp = positionTimestamp.getOrElse(defaultTimestamp)
            )
        else
            PositionProperty(
                position = position.getOrElse(defaultPosition),
                timestamp = positionTimestamp.getOrElse(defaultTimestamp)
            )

    def getPhysicsProperty: PhysicsProperty = {
        for
            physicsSelector <- physicsSelector
        yield
            PhysicsProperty(
                physicsSelector = physicsSelector
            )
    }.getOrElse(PhysicsProperty.empty)

    def getGraphicsProperty: GraphicsProperty = {
        for
            layer <- layer
            animationSelector <- animationSelector
        yield
            GraphicsProperty(
                layer = layer,
                animationSelector = animationSelector
            )
    }.getOrElse(GraphicsProperty.empty)


object GameObjectPrototype:
    private val defaultTimestamp = Timestamp.zero
    private val defaultPosition = Position(0, 0)
    private val defaultDirection = Direction.North
