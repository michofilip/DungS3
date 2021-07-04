package src.data.model

import scala.util.Try
import scala.xml.Node


case class PhysicsSelectorV2Entry(id: Int, singlePhysicsSelectorEntries: Seq[SinglePhysicsSelectorEntry])

object PhysicsSelectorV2Entry:

    def fromXML(xml: Node): Option[PhysicsSelectorV2Entry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val singlePhysicsSelectorEntries = (xml \ "single-physics-selector-entries" \ "single-physics-selector-entry")
            .flatMap(SinglePhysicsSelectorEntry.fromXML)

        PhysicsSelectorV2Entry(id, singlePhysicsSelectorEntries)
    }.toOption
