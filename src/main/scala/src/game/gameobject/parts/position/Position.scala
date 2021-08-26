package src.game.gameobject.parts.position

final case class Position(x: Int, y: Int):

    def moveBy(dx: Int, dy: Int): Position =
        new Position(x + dx, y + dy)
