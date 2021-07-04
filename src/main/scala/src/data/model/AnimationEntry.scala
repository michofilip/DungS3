package src.data.model

import src.data.file.FileReader.{Reader, *}

import scala.util.Try
import scala.xml.Node

case class AnimationEntry(id: Int, fps: Double, looping: Boolean, frameIds: Seq[Int])

object AnimationEntry:

    def reader: Reader[AnimationEntry] = strArr => Try {
        val id = strArr(0).asInt
        val fps = strArr(1).asDouble
        val looping = strArr(2).asBoolean
        val frameIds = strArr(3).asSeq(_.asInt)

        AnimationEntry(id = id, fps = fps, looping = looping, frameIds = frameIds)
    }.toOption

    def fromXML(xml: Node): Option[AnimationEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val fps = (xml \ "fps").text.trim.toDouble
        val looping = (xml \ "looping").text.trim.toBoolean
        val frameIds = (xml \ "frameIds" \ "frameId").map(_.text.trim.toInt)

        AnimationEntry(id, fps, looping, frameIds)
    }.toOption
