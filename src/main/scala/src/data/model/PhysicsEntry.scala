package src.data.model

import scala.util.Try
import scala.xml.Node

final case class PhysicsEntry(id: Int, solid: Boolean, opaque: Boolean)

object PhysicsEntry:

    def fromXML(xml: Node): Try[PhysicsEntry] = Try {
        val id = (xml \ "id").map(_.text.trim).map(_.toInt).head
        val solid = (xml \ "solid").map(_.text.trim).map(_.toBoolean).head
        val opaque = (xml \ "opaque").map(_.text.trim).map(_.toBoolean).head

        PhysicsEntry(id, solid, opaque)
    }
