package src.data.model

import scala.util.Try
import scala.xml.Node

final case class FrameEntry(id: Int, spriteId: String, offsetX: Float, offsetY: Float)

object FrameEntry:

    def fromXML(xml: Node): Option[FrameEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val spriteId = (xml \ "spriteId").text.trim
        val offsetX = (xml \ "offsetX").map(_.text.trim.toFloat).headOption.getOrElse(0f)
        val offsetY = (xml \ "offsetY").map(_.text.trim.toFloat).headOption.getOrElse(0f)

        FrameEntry(id, spriteId, offsetX, offsetY)
    }.toOption
