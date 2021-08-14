package src.game.entity.parts.position

import src.game.entity.mapper.{DirectionMapper, PositionMapper}
import src.game.entity.parts.position.{Direction, PositionProperty}
import src.game.temporal.Timestamp

sealed abstract class PositionProperty:
    val hasPosition: Boolean
    val hasDirection: Boolean

    def position: Option[Position]

    def direction: Option[Direction]

    def positionTimestamp: Option[Timestamp]

    def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty

    def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty

object PositionProperty:

    final class EmptyPositionProperty private[PositionProperty]() extends PositionProperty :
        override val hasPosition: Boolean = false
        override val hasDirection: Boolean = false

        override def position: Option[Position] = None

        override def direction: Option[Direction] = None

        override def positionTimestamp: Option[Timestamp] = None

        override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty = this

        override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty = this


    final class PositionOnlyPositionProperty private[PositionProperty](_position: Position, _positionTimestamp: Timestamp) extends PositionProperty :
        override val hasPosition: Boolean = true
        override val hasDirection: Boolean = false

        override def position: Option[Position] = Some(_position)

        override def direction: Option[Direction] = None

        override def positionTimestamp: Option[Timestamp] = Some(_positionTimestamp)

        override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty =
            positionMapper(_position) match
                case position if position != _position => new PositionOnlyPositionProperty(position, timestamp)
                case _ => this

        override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty = this


    final class PositionWithDirectionPositionProperty private[PositionProperty](_position: Position, _direction: Direction, _positionTimestamp: Timestamp) extends PositionProperty :
        override val hasPosition: Boolean = true
        override val hasDirection: Boolean = true

        override def position: Option[Position] = Some(_position)

        override def direction: Option[Direction] = Some(_direction)

        override def positionTimestamp: Option[Timestamp] = Some(_positionTimestamp)

        override def updatedPosition(positionMapper: PositionMapper, timestamp: Timestamp): PositionProperty =
            positionMapper(_position) match
                case position if position != _position => new PositionWithDirectionPositionProperty(position, _direction, timestamp)
                case _ => this

        override def updatedDirection(directionMapper: DirectionMapper, timestamp: Timestamp): PositionProperty =
            directionMapper(_direction) match
                case direction if direction != _direction => new PositionWithDirectionPositionProperty(_position, direction, timestamp)
                case _ => this


    val empty: PositionProperty =
        new EmptyPositionProperty()

    def apply(position: Position, timestamp: Timestamp): PositionProperty =
        new PositionOnlyPositionProperty(position, timestamp)

    def apply(position: Position, direction: Direction, timestamp: Timestamp): PositionProperty =
        new PositionWithDirectionPositionProperty(position, direction, timestamp)
