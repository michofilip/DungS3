package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.mapper.{DirectionMapper, PositionMapper}
import dod.game.gameobject.parts.position.PositionProperty.{EmptyPositionProperty, PositionOnlyPositionProperty, PositionWithDirectionPositionProperty}
import dod.game.gameobject.parts.position.{Direction, Position, PositionProperty}
import dod.game.gameobject.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import dod.game.temporal.Timestamp

trait PositionHolder[T <: GameObject]:
    protected val positionProperty: PositionProperty

    def hasPosition: Boolean = positionProperty.hasPosition

    def hasDirection: Boolean = positionProperty.hasDirection

    def position: Option[Position] = positionProperty.position

    def direction: Option[Direction] = positionProperty.direction

    def positionTimestamp: Option[Timestamp] = positionProperty.positionTimestamp

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): T

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): T

