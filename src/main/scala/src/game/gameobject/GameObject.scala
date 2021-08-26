package src.game.gameobject

import src.data.repository.GameObjectPrototypeRepository
import src.game.gameobject.holder.{CommonsHolder, GraphicsHolder, PhysicsHolder, PositionHolder, StateHolder}
import src.game.gameobject.mapper.{DirectionMapper, PositionMapper, StateMapper}
import src.game.gameobject.parts.graphics.{Animation, GraphicsProperty}
import src.game.gameobject.parts.physics.{PhysicsProperty, PhysicsSelector}
import src.game.gameobject.parts.position.PositionProperty
import src.game.gameobject.parts.state.StateProperty
import src.game.temporal.Timestamp

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
