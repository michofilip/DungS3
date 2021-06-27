package src.entity.repository.impl

import src.entity.EntityPrototype
import src.entity.parts.{Direction, Graphics, Physics}
import src.entity.repository.GraphicsSelectorRepository
import src.entity.selector.GraphicsSelector

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
