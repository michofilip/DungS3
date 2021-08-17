package src.data.model

import src.exception.FailedToReadObject

import scala.util.{Failure, Success, Try}
import scala.xml.Node

final case class FrameEntry(id: Int, spriteId: String, offsetX: Float, offsetY: Float)

object FrameEntry:

    def fromXML(xml: Node): Try[FrameEntry] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            spriteId <- Try((xml \ "spriteId").map(_.text.trim).head)
            offsetX <- Try((xml \ "offsetX").map(_.text.trim).map(_.toFloat).headOption.getOrElse(0f))
            offsetY <- Try((xml \ "offsetY").map(_.text.trim).map(_.toFloat).headOption.getOrElse(0f))
        yield
            FrameEntry(id, spriteId, offsetX, offsetY)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("FrameEntry", e.getMessage))
    }
