package src.data.model

import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.Node

final case class PhysicsSelectorEntry(id: Int, variants: Seq[PhysicsSelectorVariantEntry])

object PhysicsSelectorEntry:

    def fromXML(xml: Node): Try[PhysicsSelectorEntry] =
        val id = Try((xml \ "id").text.trim.toInt)
        val variants = (xml \ "variants" \ "PhysicsSelectorVariant")
            .map(PhysicsSelectorVariantEntry.fromXML)
            .invertTry

        for {
            id <- id
            variants <- variants
        } yield {
            PhysicsSelectorEntry(id, variants)
        }
