package src.data.model

import src.data.file.FileReader.{Reader, *}

import scala.util.Try

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
