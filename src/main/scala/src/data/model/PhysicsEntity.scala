package src.data.model

import src.exception.FailedToReadObject

import scala.util.{Failure, Try}
import scala.xml.Node

final case class PhysicsEntity(id: Int, solid: Boolean, opaque: Boolean)

object PhysicsEntity:

    def fromXML(xml: Node): Try[PhysicsEntity] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            solid <- Try((xml \ "solid").map(_.text.trim).map(_.toBoolean).head)
            opaque <- Try((xml \ "opaque").map(_.text.trim).map(_.toBoolean).head)
        yield
            PhysicsEntity(id, solid, opaque)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("PhysicsEntity", e.getMessage))
    }
