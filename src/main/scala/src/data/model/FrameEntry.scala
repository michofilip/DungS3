package src.data.model

import scala.util.Try
import scala.xml.Node

final case class FrameEntry(id: Int, imageId: Int, layer: Int, offsetX: Float, offsetY: Float)

object FrameEntry:

    def fromXML(xml: Node): Option[FrameEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val imageId = (xml \ "imageId").text.trim.toInt
        val layer = (xml \ "layer").text.trim.toInt
        val offsetX = (xml \ "offsetX").map(_.text.trim.toFloat).headOption.getOrElse(0f)
        val offsetY = (xml \ "offsetY").map(_.text.trim.toFloat).headOption.getOrElse(0f)

        FrameEntry(id, imageId, layer, offsetX, offsetY)
    }.toOption
