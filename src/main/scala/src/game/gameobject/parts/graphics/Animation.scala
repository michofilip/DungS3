package src.game.gameobject.parts.graphics

import src.game.gameobject.parts.graphics.Frame
import src.game.temporal.Duration
import src.game.temporal.Duration.*
import src.utils.MathUtils
import src.utils.MathUtils.*

import scala.concurrent.duration

abstract class Animation(val fps: Double, private val frames: IndexedSeq[Frame]):
    val length: Duration = (frames.length / fps * 1000).milliseconds

    def frame(duration: Duration): Frame =
        val frameNo = MathUtils.floor(duration.milliseconds * fps / 1000)
        frames(frameIndex(frameNo, frames.length))

    protected def frameIndex(frameNo: Int, frameLength: Int): Int

object Animation:

    final class LoopingAnimation(fps: Double, frames: IndexedSeq[Frame]) extends Animation(fps, frames) :
        override protected def frameIndex(frameNo: Int, frameLength: Int): Int = frameNo %% frameLength

    final class SingleRunAnimation(fps: Double, frames: IndexedSeq[Frame]) extends Animation(fps, frames) :
        override protected def frameIndex(frameNo: Int, frameLength: Int): Int = frameNo >< (0, frameLength - 1)

    def apply(fps: Double, frames: Seq[Frame], looping: Boolean): Animation =
        if looping then
            LoopingAnimation(fps = fps, frames = frames.toIndexedSeq)
        else
            SingleRunAnimation(fps = fps, frames = frames.toIndexedSeq)
            