package src.data.model

import scala.util.Try
import scala.xml.Node


final case class PhysicsSelectorEntry(id: Int, variants: Seq[PhysicsSelectorVariantEntry])

object PhysicsSelectorEntry:

    def fromXML(xml: Node): Option[PhysicsSelectorEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val variants = (xml \ "variants" \ "PhysicsSelectorVariant")
            .flatMap(PhysicsSelectorVariantEntry.fromXML)

        PhysicsSelectorEntry(id, variants)
    }.toOption
