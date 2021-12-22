package dod.game.gameobject

import dod.game.gameobject.GameObjectPrototype.*
import dod.game.gameobject.parts.commons.CommonsProperty
import dod.game.gameobject.parts.graphics.{AnimationSelector, GraphicsProperty}
import dod.game.gameobject.parts.physics.{PhysicsProperty, PhysicsSelector}
import dod.game.gameobject.parts.position.{Direction, Position, PositionProperty}
import dod.game.gameobject.parts.state.{State, StateProperty}
import dod.game.temporal.Timestamp

final class GameObjectPrototype(private val name: String,
                                private val availableStates: Seq[State],
                                private val hasPosition: Boolean,
                                private val layer: Option[Int],
                                private val physicsSelector: Option[PhysicsSelector],
                                private val animationSelector: Option[AnimationSelector]) {

    def getCommonsProperty(creationTimestamp: Timestamp): CommonsProperty =
        CommonsProperty(name, creationTimestamp)

    def getStateProperty(state: Option[State], stateTimestamp: Option[Timestamp]): Option[StateProperty] =
        if availableStates.isEmpty then
            None
        else state match
            case Some(state) if availableStates.contains(state) => Some(StateProperty(state, stateTimestamp.getOrElse(defaultTimestamp)))
            case _ => Some(StateProperty(availableStates.head, stateTimestamp.getOrElse(defaultTimestamp)))

    def getPositionProperty(position: Option[Position], direction: Option[Direction], positionTimestamp: Option[Timestamp]): Option[PositionProperty] =
        if hasPosition then
            Some(PositionProperty(
                position = position.getOrElse(defaultPosition),
                direction = direction.getOrElse(defaultDirection),
                positionTimestamp = positionTimestamp.getOrElse(defaultTimestamp)
            ))
        else
            None

    def getPhysicsProperty: Option[PhysicsProperty] = {
        for
            physicsSelector <- physicsSelector
        yield
            PhysicsProperty(
                physicsSelector = physicsSelector
            )
    }

    def getGraphicsProperty: Option[GraphicsProperty] = {
        for
            layer <- layer
            animationSelector <- animationSelector
        yield
            GraphicsProperty(
                layer = layer,
                animationSelector = animationSelector
            )
    }
}

object GameObjectPrototype {
    private val defaultTimestamp = Timestamp.zero
    private val defaultPosition = Position(0, 0)
    private val defaultDirection = Direction.North
}
