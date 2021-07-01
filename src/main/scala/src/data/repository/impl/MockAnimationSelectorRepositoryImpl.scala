package src.data.repository.impl

import src.data.repository.{AnimationSelectorRepository, GraphicsSelectorRepository}
import src.game.entity.EntityPrototype
import src.game.entity.parts.animation.{Animation, Frame, LoopingAnimation}
import src.game.entity.parts.{Direction, Graphics, Physics}
import src.game.entity.selector.{AnimationSelector, GraphicsSelector}

final class MockAnimationSelectorRepositoryImpl() extends AnimationSelectorRepository :
    override def findByName(name: String): Option[AnimationSelector] =
        Some {
            AnimationSelector(
                (None, Some(Direction.North)) -> LoopingAnimation(fps = 30, frames = IndexedSeq(Frame(imageId = 0, layer = 0))),
                (None, Some(Direction.East)) -> LoopingAnimation(fps = 30, frames = IndexedSeq(Frame(imageId = 1, layer = 0))),
                (None, Some(Direction.South)) -> LoopingAnimation(fps = 30, frames = IndexedSeq(Frame(imageId = 2, layer = 0))),
                (None, Some(Direction.West)) -> LoopingAnimation(fps = 30, frames = IndexedSeq(Frame(imageId = 3, layer = 0)))
            )
        }
