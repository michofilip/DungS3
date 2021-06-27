package src.game.entity.mapper

import src.game.entity.parts.Direction

sealed abstract class DirectionMapper extends Mapper[Direction]

object DirectionMapper:

    case object Identity extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] = directionOpt

    final case class SetDirection(direction: Direction) extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] = Some(direction)

    case object RemoveDirection extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] = None

    final case class TurnTo(direction: Direction) extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] =
            directionOpt.map(_ => direction)

    case object TurnRight extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] =
            directionOpt.map {
                case Direction.North => Direction.East
                case Direction.East => Direction.South
                case Direction.South => Direction.West
                case Direction.West => Direction.North
            }

    case object TurnLeft extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] =
            directionOpt.map {
                case Direction.North => Direction.West
                case Direction.East => Direction.North
                case Direction.South => Direction.East
                case Direction.West => Direction.South
            }

    case object TurnBack extends DirectionMapper :
        override def apply(directionOpt: Option[Direction]): Option[Direction] =
            directionOpt.map {
                case Direction.North => Direction.South
                case Direction.East => Direction.West
                case Direction.South => Direction.North
                case Direction.West => Direction.East
            }
        