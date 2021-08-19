package src.data.model

import src.exception.FailedToReadObject
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.Node

final case class PhysicsSelectorEntry(id: Int, variants: Seq[PhysicsSelectorVariantEntry])

object PhysicsSelectorEntry:

    def fromXML(xml: Node): Try[PhysicsSelectorEntry] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            variants <- (xml \ "variants" \ "PhysicsSelectorVariant").map(PhysicsSelectorVariantEntry.fromXML).invertTry
        yield
            PhysicsSelectorEntry(id, variants)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("PhysicsSelectorEntry", e.getMessage))
    }
