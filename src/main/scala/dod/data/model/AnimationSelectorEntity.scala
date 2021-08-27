package dod.data.model

import dod.exception.FailedToReadObject
import dod.utils.TryUtils.*

import scala.util.{Failure, Try}
import scala.xml.Node

final case class AnimationSelectorEntity(id: Int, variants: Seq[AnimationSelectorVariantEntity])

object AnimationSelectorEntity:

    def fromXML(xml: Node): Try[AnimationSelectorEntity] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            variants <- (xml \ "variants" \ "AnimationSelectorVariant").map(AnimationSelectorVariantEntity.fromXML).toTrySeq
        yield
            AnimationSelectorEntity(id, variants)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("AnimationSelectorEntity", e.getMessage))
    }
