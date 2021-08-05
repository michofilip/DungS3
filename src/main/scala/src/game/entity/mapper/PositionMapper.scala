package src.game.entity.mapper

import src.game.entity.parts.position.Position

sealed abstract class PositionMapper extends Mapper[Position]

object PositionMapper:

    @Deprecated
    case object Identity extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] = positionOpt

    @Deprecated
    final case class SetPosition(position: Position) extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] = Some(position)

    @Deprecated
    case object RemovePosition extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] = None

    final case class MoveTo(x: Int, y: Int) extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] =
            positionOpt.map(_ => Position(x, y))

    final case class MoveBy(dx: Int, dy: Int) extends PositionMapper :
        override def apply(positionOpt: Option[Position]): Option[Position] =
            positionOpt.map(position => position.moveBy(dx, dy))
