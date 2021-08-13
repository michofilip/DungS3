package src.game.entity.parts.position

import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.position.{Direction, PositionProperty}
import src.game.temporal.Timestamp

sealed abstract class PositionProperty:
    val hasPosition: Boolean
    val hasDirection: Boolean

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty

object PositionProperty:

    case object EmptyPositionProperty extends PositionProperty :
        override val hasPosition: Boolean = false
        override val hasDirection: Boolean = false

        override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty = this

        override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty = this


    final case class PositionOnlyPositionProperty(position: Position, positionTimestamp: Timestamp) extends PositionProperty :
        override val hasPosition: Boolean = true
        override val hasDirection: Boolean = false

        override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty =
            positionMapper(position) match
                case position if position != this.position => copy(position = position, positionTimestamp = timestamp)
                case _ => this

        override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty = this


    final case class PositionWithDirectionPositionProperty(position: Position, direction: Direction, positionTimestamp: Timestamp) extends PositionProperty :
        override val hasPosition: Boolean = true
        override val hasDirection: Boolean = true

        override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty =
            positionMapper(position) match
                case position if position != this.position => copy(position = position, positionTimestamp = timestamp)
                case _ => this

        override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty =
            directionMapper(direction) match
                case direction if direction != this.direction => copy(direction = direction, positionTimestamp = timestamp)
                case _ => this


    val empty: PositionProperty = EmptyPositionProperty

    def apply(position: Position, timestamp: Timestamp): PositionProperty =
        PositionOnlyPositionProperty(position = position, positionTimestamp = timestamp)

    def apply(position: Position, direction: Direction, timestamp: Timestamp): PositionProperty =
        PositionWithDirectionPositionProperty(position = position, direction = direction, positionTimestamp = timestamp)
