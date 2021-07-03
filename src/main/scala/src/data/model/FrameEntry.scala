package src.data.model

import src.data.file.FileReader.Reader

import scala.util.Try

final case class FrameEntry(id: Int, imageId: Int, layer: Int, offsetX: Float, offsetY: Float)

object FrameEntry:
    
    val reader: Reader[FrameEntry] = strArr => Try {
        val id = strArr(0).toInt
        val imageId = strArr(1).toInt
        val layer = strArr(2).toInt
        val offsetX = strArr(3).toFloat
        val offsetY = strArr(4).toFloat

        FrameEntry(id = id, imageId = imageId, layer = layer, offsetX = offsetX, offsetY = offsetY)
    }.toOption
