package src.game.gameobject

import src.data.repository.EntityPrototypeRepository
import src.game.gameobject.holder.{CommonsHolder, GraphicsHolder, PhysicsHolder, PositionHolder, StateHolder}
import src.game.gameobject.mapper.{DirectionMapper, PositionMapper, StateMapper}
import src.game.gameobject.parts.graphics.{Animation, GraphicsProperty}
import src.game.gameobject.parts.physics.{PhysicsProperty, PhysicsSelector}
import src.game.gameobject.parts.position.PositionProperty
import src.game.gameobject.parts.state.StateProperty
import src.game.temporal.Timestamp

import java.util.UUID

final class Entity(override val id: UUID,
                   override val name: String,
                   override val creationTimestamp: Timestamp,
                   override protected val stateProperty: StateProperty,
                   override protected val positionProperty: PositionProperty,
                   override protected val physicsProperty: PhysicsProperty,
                   override protected val graphicsProperty: GraphicsProperty)
    extends CommonsHolder
        with StateHolder[Entity]
        with PositionHolder[Entity]
        with PhysicsHolder[Entity]
        with GraphicsHolder[Entity] :

    private def copy(stateProperty: StateProperty = stateProperty,
                     positionProperty: PositionProperty = positionProperty,
                     physicsProperty: PhysicsProperty = physicsProperty,
                     graphicsProperty: GraphicsProperty = graphicsProperty): Entity =
        new Entity(id, name, creationTimestamp, stateProperty, positionProperty, physicsProperty, graphicsProperty)

    override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): Entity =
        copy(stateProperty = stateProperty.updatedState(stateMapper, timestamp))

    override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): Entity =
        copy(positionProperty = positionProperty.updatedPosition(positionMapper, timestamp))

    override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): Entity =
        copy(positionProperty = positionProperty.updatedDirection(directionMapper, timestamp))
