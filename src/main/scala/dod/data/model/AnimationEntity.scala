package dod.data.model

import dod.exception.FailedToReadObject

import scala.util.{Failure, Try}
import scala.xml.Node

final case class AnimationEntity(id: Int, fps: Double, looping: Boolean, frameIds: Seq[Int])

object AnimationEntity:

    def fromXML(xml: Node): Try[AnimationEntity] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            fps <- Try((xml \ "fps").map(_.text.trim).map(_.toDouble).head)
            looping <- Try((xml \ "looping").map(_.text.trim).map(_.toBoolean).head)
            frameIds <- Try((xml \ "frameIds" \ "frameId").map(_.text.trim).map(_.toInt))
        yield
            AnimationEntity(id, fps, looping, frameIds)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("AnimationEntity", e.getMessage))
    }
