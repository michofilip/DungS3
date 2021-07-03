package src.data.model

import src.data.file.FileReader.Reader

import scala.util.Try

case class AnimationEntry(id: Int, fps: Double, isLooping: Boolean, frameIds: Seq[Int])

object AnimationEntry:
    
    def reader: Reader[AnimationEntry] = strArr => Try {
        val id = strArr(0).toInt
        val fps = strArr(1).toDouble
        val isLooping = strArr(2) == "1"
        val frameIds = strArr(3).split(',').map(_.trim).filter(_.nonEmpty).map(_.toInt).toSeq

        AnimationEntry(id = id, fps = fps, isLooping = isLooping, frameIds = frameIds)
    }.toOption
