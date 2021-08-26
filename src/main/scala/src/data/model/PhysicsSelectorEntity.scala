package src.data.model

import src.exception.FailedToReadObject
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.Node

final case class PhysicsSelectorEntity(id: Int, variants: Seq[PhysicsSelectorVariantEntity])

object PhysicsSelectorEntity:

    def fromXML(xml: Node): Try[PhysicsSelectorEntity] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            variants <- (xml \ "variants" \ "PhysicsSelectorVariant").map(PhysicsSelectorVariantEntity.fromXML).toTrySeq
        yield
            PhysicsSelectorEntity(id, variants)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("PhysicsSelectorEntity", e.getMessage))
    }
