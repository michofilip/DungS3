package src.game.entity.parts.animation

import src.game.utils.MathUtils

final class SingleRunAnimation(fps: Double, frames: IndexedSeq[Frame]) extends Animation(fps, frames) :
        override protected def frameIndex(frameNo: Int, frameLength: Int): Int = MathUtils.bound(frameNo, 0, frameLength - 1)