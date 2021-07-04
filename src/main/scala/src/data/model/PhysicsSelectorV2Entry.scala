package src.data.model

import scala.util.Try
import scala.xml.Node


case class PhysicsSelectorV2Entry(id: Int, variants: Seq[PhysicsSelectorVariantEntry])

object PhysicsSelectorV2Entry:

    def fromXML(xml: Node): Option[PhysicsSelectorV2Entry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val variants = (xml \ "variants" \ "PhysicsSelectorVariant")
            .flatMap(PhysicsSelectorVariantEntry.fromXML)

        PhysicsSelectorV2Entry(id, variants)
    }.toOption
