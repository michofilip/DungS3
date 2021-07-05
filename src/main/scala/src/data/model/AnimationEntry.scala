package src.data.model

import scala.util.Try
import scala.xml.Node

case class AnimationEntry(id: Int, fps: Double, looping: Boolean, frameIds: Seq[Int])

object AnimationEntry:

    def fromXML(xml: Node): Option[AnimationEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val fps = (xml \ "fps").text.trim.toDouble
        val looping = (xml \ "looping").text.trim.toBoolean
        val frameIds = (xml \ "frameIds" \ "frameId").map(_.text.trim.toInt)

        AnimationEntry(id, fps, looping, frameIds)
    }.toOption
