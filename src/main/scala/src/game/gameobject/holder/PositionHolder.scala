package src.game.gameobject.holder

import src.game.gameobject.Entity
import src.game.gameobject.mapper.{DirectionMapper, PositionMapper}
import src.game.gameobject.parts.position.PositionProperty.{EmptyPositionProperty, PositionOnlyPositionProperty, PositionWithDirectionPositionProperty}
import src.game.gameobject.parts.position.{Direction, Position, PositionProperty}
import src.game.gameobject.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import src.game.temporal.Timestamp

trait PositionHolder[T <: Entity]:
    protected val positionProperty: PositionProperty

    def hasPosition: Boolean = positionProperty.hasPosition

    def hasDirection: Boolean = positionProperty.hasDirection

    def position: Option[Position] = positionProperty.position

    def direction: Option[Direction] = positionProperty.direction

    def positionTimestamp: Option[Timestamp] = positionProperty.positionTimestamp

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): T

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): T

