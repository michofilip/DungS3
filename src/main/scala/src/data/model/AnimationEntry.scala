package src.data.model

import src.exception.FailedToReadObject

import scala.util.{Failure, Try}
import scala.xml.Node

final case class AnimationEntry(id: Int, fps: Double, looping: Boolean, frameIds: Seq[Int])

object AnimationEntry:

    def fromXML(xml: Node): Try[AnimationEntry] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            fps <- Try((xml \ "fps").map(_.text.trim).map(_.toDouble).head)
            looping <- Try((xml \ "looping").map(_.text.trim).map(_.toBoolean).head)
            frameIds <- Try((xml \ "frameIds" \ "frameId").map(_.text.trim).map(_.toInt))
        yield
            AnimationEntry(id, fps, looping, frameIds)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("AnimationEntry", e.getMessage))
    }
