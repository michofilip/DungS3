package src.data.model

import src.data.file.FileReader.{Reader, *}

import scala.util.Try

case class AnimationEntry(id: Int, fps: Double, isLooping: Boolean, frameIds: Seq[Int])

object AnimationEntry:

    def reader: Reader[AnimationEntry] = strArr => Try {
        val id = strArr(0).asInt
        val fps = strArr(1).asDouble
        val isLooping = strArr(2).asBoolean
        val frameIds = strArr(3).asSeq(_.asInt)

        AnimationEntry(id = id, fps = fps, isLooping = isLooping, frameIds = frameIds)
    }.toOption
