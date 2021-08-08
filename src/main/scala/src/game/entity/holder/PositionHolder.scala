package src.game.entity.holder

import src.game.entity.parts.position.{Direction, Position, PositionProperty}
import src.game.temporal.Timestamp

trait PositionHolder:
    protected val positionProperty: PositionProperty

    def position: Option[Position] = positionProperty.position

    def direction: Option[Direction] = positionProperty.direction

    def positionTimestamp: Option[Timestamp] = positionProperty.positionTimestamp

    def hasPosition: Boolean = position.isDefined

    def hasDirection: Boolean = direction.isDefined
