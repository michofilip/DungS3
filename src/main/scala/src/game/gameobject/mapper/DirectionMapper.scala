package src.game.gameobject.mapper

import src.game.gameobject.parts.position.Direction

sealed abstract class DirectionMapper extends Mapper[Direction]

object DirectionMapper:

    final case class TurnTo(direction: Direction) extends DirectionMapper :
        override def apply(direction: Direction): Direction = direction

    case object TurnRight extends DirectionMapper :
        override def apply(direction: Direction): Direction = direction match {
            case Direction.North => Direction.East
            case Direction.East => Direction.South
            case Direction.South => Direction.West
            case Direction.West => Direction.North
        }

    case object TurnLeft extends DirectionMapper :
        override def apply(direction: Direction): Direction = direction match {
            case Direction.North => Direction.West
            case Direction.East => Direction.North
            case Direction.South => Direction.East
            case Direction.West => Direction.South
        }

    case object TurnBack extends DirectionMapper :
        override def apply(direction: Direction): Direction = direction match {
            case Direction.North => Direction.South
            case Direction.East => Direction.West
            case Direction.South => Direction.North
            case Direction.West => Direction.East
        }
        