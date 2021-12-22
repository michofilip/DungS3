package dod.game.gameobject

import dod.data.repository.GameObjectPrototypeRepository
import dod.game.gameobject.holder.{CommonsHolder, GraphicsHolder, PhysicsHolder, PositionHolder, StateHolder}
import dod.game.gameobject.mapper.{DirectionMapper, PositionMapper, StateMapper}
import dod.game.gameobject.parts.commons.CommonsProperty
import dod.game.gameobject.parts.graphics.{Animation, GraphicsProperty}
import dod.game.gameobject.parts.physics.{PhysicsProperty, PhysicsSelector}
import dod.game.gameobject.parts.position.PositionProperty
import dod.game.gameobject.parts.state.StateProperty
import dod.game.temporal.Timestamp

import java.util.UUID

final class GameObject(val id: UUID,
                       override protected val commonsProperty: CommonsProperty,
                       override protected val stateProperty: Option[StateProperty],
                       override protected val positionProperty: Option[PositionProperty],
                       override protected val physicsProperty: Option[PhysicsProperty],
                       override protected val graphicsProperty: Option[GraphicsProperty])
    extends CommonsHolder
        with StateHolder[GameObject]
        with PositionHolder[GameObject]
        with PhysicsHolder[GameObject]
        with GraphicsHolder[GameObject] {

    private def copy(stateProperty: Option[StateProperty] = stateProperty,
                     positionProperty: Option[PositionProperty] = positionProperty,
                     physicsProperty: Option[PhysicsProperty] = physicsProperty,
                     graphicsProperty: Option[GraphicsProperty] = graphicsProperty): GameObject =
        new GameObject(id, commonsProperty, stateProperty, positionProperty, physicsProperty, graphicsProperty)

    override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): GameObject =
        copy(stateProperty = stateProperty.map(_.updatedState(stateMapper, timestamp)))

    override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): GameObject =
        copy(positionProperty = positionProperty.map(_.updatedPosition(positionMapper, timestamp)))

    override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): GameObject =
        copy(positionProperty = positionProperty.map(_.updatedDirection(directionMapper, timestamp)))
}