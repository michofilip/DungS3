package src.data.model

import scala.util.Try
import scala.xml.Node

final case class PhysicsEntry(id: Int, solid: Boolean, opaque: Boolean)

object PhysicsEntry:

    def fromXML(xml: Node): Try[PhysicsEntry] =
        val id = Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
        val solid = Try((xml \ "solid").map(_.text.trim).map(_.toBoolean).head)
        val opaque = Try((xml \ "opaque").map(_.text.trim).map(_.toBoolean).head)

        for {
            id <- id
            solid <- solid
            opaque <- opaque
        } yield {
            PhysicsEntry(id, solid, opaque)
        }
