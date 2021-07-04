package src.data.model

import src.data.file.FileReader.{Reader, *}

import scala.util.Try
import scala.xml.Node

final case class FrameEntry(id: Int, imageId: Int, layer: Int, offsetX: Float, offsetY: Float)

object FrameEntry:

    val reader: Reader[FrameEntry] = strArr => Try {
        val id = strArr(0).asInt
        val imageId = strArr(1).asInt
        val layer = strArr(2).asInt
        val offsetX = strArr(3).asFloat
        val offsetY = strArr(4).asFloat

        FrameEntry(id = id, imageId = imageId, layer = layer, offsetX = offsetX, offsetY = offsetY)
    }.toOption

    def fromXML(xml: Node): Option[FrameEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val imageId = (xml \ "imageId").text.trim.toInt
        val layer = (xml \ "layer").text.trim.toInt
        val offsetX = (xml \ "offsetX").map(_.text.trim.toFloat).headOption.getOrElse(0f)
        val offsetY = (xml \ "offsetY").map(_.text.trim.toFloat).headOption.getOrElse(0f)

        FrameEntry(id, imageId, layer, offsetX, offsetY)
    }.toOption
