package src.data.repository.impl

import src.data.repository.GraphicsSelectorRepository
import src.game.entity.EntityPrototype
import src.game.entity.parts.{Direction, Graphics, Physics}
import src.game.entity.selector.GraphicsSelector

@Deprecated
final class MockGraphicsSelectorRepositoryImpl() extends GraphicsSelectorRepository :
    override def findByName(name: String): Option[GraphicsSelector] =
        Some {
            GraphicsSelector(
                (None, Some(Direction.North)) -> Graphics(imageId = 0, layer = 0),
                (None, Some(Direction.East)) -> Graphics(imageId = 1, layer = 0),
                (None, Some(Direction.South)) -> Graphics(imageId = 2, layer = 0),
                (None, Some(Direction.West)) -> Graphics(imageId = 3, layer = 0)
            )
        }
