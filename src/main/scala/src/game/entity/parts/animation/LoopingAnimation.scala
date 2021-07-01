package src.game.entity.parts.animation

import src.game.utils.MathUtils

final class LoopingAnimation(fps: Double, frames: IndexedSeq[Frame]) extends Animation(fps, frames) :
        override protected def frameIndex(frameNo: Int, frameLength: Int): Int = MathUtils.mod(frameNo, frameLength)