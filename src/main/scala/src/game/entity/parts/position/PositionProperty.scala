package src.game.entity.parts.position

import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.position.{Direction, PositionProperty}
import src.game.temporal.Timestamp

class PositionProperty private(val position: Option[Position], val direction: Option[Direction], val positionTimestamp: Option[Timestamp]):

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty =
        val newPosition = positionMapper(position)

        if position != newPosition then
            new PositionProperty(newPosition, direction, Some(timestamp))
        else
            this

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty =
        val newDirection = directionMapper(direction)

        if direction != newDirection then
            new PositionProperty(position, newDirection, Some(timestamp))
        else
            this

object PositionProperty:

    lazy val empty: PositionProperty =
        new PositionProperty(
            position = None,
            direction = None,
            positionTimestamp = None
        )

    def apply(position: Position, timestamp: Timestamp): PositionProperty =
        new PositionProperty(
            position = Some(position),
            direction = None,
            positionTimestamp = Some(timestamp)
        )


    def apply(position: Position, direction: Direction, timestamp: Timestamp): PositionProperty =
        new PositionProperty(
            position = Some(position),
            direction = Some(direction),
            positionTimestamp = Some(timestamp)
        )
