package src.data.model

import src.exception.FailedToReadObject

import scala.util.{Failure, Success, Try}
import scala.xml.Node

final case class FrameEntry(id: Int, spriteId: String, offsetX: Float, offsetY: Float)

object FrameEntry:

    def fromXML(xml: Node): Try[FrameEntry] = Try {
        val id = (xml \ "id").map(_.text.trim).map(_.toInt).head
        val spriteId = (xml \ "spriteId").map(_.text.trim).head
        val offsetX = (xml \ "offsetX").map(_.text.trim).map(_.toFloat).headOption.getOrElse(0f)
        val offsetY = (xml \ "offsetY").map(_.text.trim).map(_.toFloat).headOption.getOrElse(0f)

        FrameEntry(id, spriteId, offsetX, offsetY)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("FrameEntry", e.getMessage))
    }
    