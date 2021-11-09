package dod.game.gameobject

import dod.data.repository.GameObjectPrototypeRepository
import dod.game.gameobject.holder.{CommonsHolder, GraphicsHolder, PhysicsHolder, PositionHolder, StateHolder}
import dod.game.gameobject.mapper.{DirectionMapper, PositionMapper, StateMapper}
import dod.game.gameobject.parts.graphics.{Animation, GraphicsProperty}
import dod.game.gameobject.parts.physics.{PhysicsProperty, PhysicsSelector}
import dod.game.gameobject.parts.position.PositionProperty
import dod.game.gameobject.parts.state.StateProperty
import dod.game.temporal.Timestamp

import java.util.UUID

final class GameObject(override val id: UUID,
                       override val name: String,
                       override val creationTimestamp: Timestamp,
                       override protected val stateProperty: StateProperty,
                       override protected val positionProperty: PositionProperty,
                       override protected val physicsProperty: PhysicsProperty,
                       override protected val graphicsProperty: GraphicsProperty)
    extends CommonsHolder
        with StateHolder[GameObject]
        with PositionHolder[GameObject]
        with PhysicsHolder[GameObject]
        with GraphicsHolder[GameObject] :

    private def copy(stateProperty: StateProperty = stateProperty,
                     positionProperty: PositionProperty = positionProperty,
                     physicsProperty: PhysicsProperty = physicsProperty,
                     graphicsProperty: GraphicsProperty = graphicsProperty): GameObject =
        new GameObject(id, name, creationTimestamp, stateProperty, positionProperty, physicsProperty, graphicsProperty)

    override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): GameObject =
        copy(stateProperty = stateProperty.updatedState(stateMapper, timestamp))

    override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): GameObject =
        copy(positionProperty = positionProperty.updatedPosition(positionMapper, timestamp))

    override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): GameObject =
        copy(positionProperty = positionProperty.updatedDirection(directionMapper, timestamp))
