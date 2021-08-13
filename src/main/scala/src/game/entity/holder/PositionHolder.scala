package src.game.entity.holder

import src.game.entity.parts.position.PositionProperty.{EmptyPositionProperty, PositionOnlyPositionProperty, PositionWithDirectionPositionProperty}
import src.game.entity.parts.position.{Direction, Position, PositionProperty}
import src.game.entity.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import src.game.temporal.Timestamp

trait PositionHolder:
    protected val positionProperty: PositionProperty

    def hasPosition: Boolean = positionProperty.hasPosition

    def hasDirection: Boolean = positionProperty.hasDirection

    def position: Option[Position] = positionProperty match {
        case EmptyPositionProperty => None
        case PositionOnlyPositionProperty(position, _) => Some(position)
        case PositionWithDirectionPositionProperty(position, _, _) => Some(position)
    }

    def direction: Option[Direction] = positionProperty match {
        case EmptyPositionProperty => None
        case PositionOnlyPositionProperty(_, _) => None
        case PositionWithDirectionPositionProperty(_, direction, _) => Some(direction)
    }

    def positionTimestamp: Option[Timestamp] = positionProperty match {
        case EmptyPositionProperty => None
        case PositionOnlyPositionProperty(_, positionTimestamp) => Some(positionTimestamp)
        case PositionWithDirectionPositionProperty(_, _, positionTimestamp) => Some(positionTimestamp)
    }

