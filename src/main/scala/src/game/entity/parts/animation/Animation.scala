package src.game.entity.parts.animation

import src.game.entity.parts.animation.Frame
import src.game.temporal.Duration
import src.game.temporal.Duration.*
import src.utils.MathUtils

import scala.concurrent.duration

abstract class Animation(val fps: Double, private val frames: IndexedSeq[Frame]):
    val length: Duration = (frames.length / fps * 1000).milliseconds

    def frame(duration: Duration): Frame =
        val frameNo = MathUtils.floor(duration.milliseconds * fps / 1000)
        frames(frameIndex(frameNo, frames.length))

    protected def frameIndex(frameNo: Int, frameLength: Int): Int
    