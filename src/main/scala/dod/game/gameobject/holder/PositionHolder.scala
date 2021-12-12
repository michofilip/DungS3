package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.mapper.{DirectionMapper, PositionMapper}
import dod.game.gameobject.parts.position.{Direction, Position, PositionProperty}
import dod.game.temporal.Timestamp

trait PositionHolder[T <: GameObject]:
    protected val positionProperty: Option[PositionProperty]

    def hasPosition: Boolean = positionProperty.isDefined

    def position: Option[Position] = positionProperty.map(_.position)

    def direction: Option[Direction] = positionProperty.map(_.direction)

    def positionTimestamp: Option[Timestamp] = positionProperty.map(_.positionTimestamp)

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): T

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): T

