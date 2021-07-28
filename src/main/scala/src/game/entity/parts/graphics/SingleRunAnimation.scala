package src.game.entity.parts.graphics

import src.utils.MathUtils.*

final class SingleRunAnimation(fps: Double, frames: IndexedSeq[Frame]) extends Animation(fps, frames) :

    override protected def frameIndex(frameNo: Int, frameLength: Int): Int = frameNo >< (0, frameLength - 1)