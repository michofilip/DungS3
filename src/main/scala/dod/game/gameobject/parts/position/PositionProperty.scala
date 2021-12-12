package dod.game.gameobject.parts.position

import dod.game.gameobject.mapper.{DirectionMapper, PositionMapper}
import dod.game.gameobject.parts.position.{Direction, PositionProperty}
import dod.game.temporal.Timestamp

class PositionProperty(val position: Position,
                       val direction: Direction,
                       val positionTimestamp: Timestamp):

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty =
        positionMapper(position) match
            case position if position != this.position => new PositionProperty(position, direction, timestamp)
            case _ => this

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty =
        directionMapper(direction) match
            case direction if direction != this.direction => new PositionProperty(position, direction, timestamp)
            case _ => this
            