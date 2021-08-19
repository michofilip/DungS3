package src.game.entity.mapper

import src.game.entity.parts.position.Position

sealed abstract class PositionMapper extends Mapper[Position]

object PositionMapper:

    final case class MoveTo(x: Int, y: Int) extends PositionMapper :
        override def apply(position: Position): Position =
            Position(x, y)

    final case class MoveBy(dx: Int, dy: Int) extends PositionMapper :
        override def apply(position: Position): Position =
            position.moveBy(dx, dy)
