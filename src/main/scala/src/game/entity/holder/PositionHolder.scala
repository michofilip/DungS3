package src.game.entity.holder

import src.game.entity.Entity
import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.position.PositionProperty.{EmptyPositionProperty, PositionOnlyPositionProperty, PositionWithDirectionPositionProperty}
import src.game.entity.parts.position.{Direction, Position, PositionProperty}
import src.game.entity.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
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

