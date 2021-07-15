package src.data.model

import scala.util.Try
import scala.xml.Node

final case class PhysicsEntry(id: Int, solid: Boolean, opaque: Boolean)

object PhysicsEntry:

    def fromXML(xml: Node): Option[PhysicsEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val solid = (xml \ "solid").text.trim.toBoolean
        val opaque = (xml \ "opaque").text.trim.toBoolean

        PhysicsEntry(id, solid, opaque)
    }.toOption
