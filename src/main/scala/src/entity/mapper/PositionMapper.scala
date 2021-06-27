package src.entity.mapper

import src.entity.parts.Position

sealed abstract class PositionMapper extends Mapper[Position]

object PositionMapper:

    case object Identity extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] = positionOpt

    final case class SetPosition(position: Position) extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] = Some(position)

    case object RemovePosition extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] = None

    final case class MoveTo(x: Int, y: Int) extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] =
            positionOpt.map(_ => Position(x, y))

    final case class MoveBy(dx: Int, dy: Int) extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] =
            positionOpt.map(position => position.moveBy(dx, dy))
