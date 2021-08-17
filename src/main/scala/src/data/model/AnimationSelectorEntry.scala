package src.data.model

import src.exception.FailedToReadObject
import src.utils.TryUtils.*

import scala.util.{Failure, Try}
import scala.xml.Node

final case class AnimationSelectorEntry(id: Int, variants: Seq[AnimationSelectorVariantEntry])

object AnimationSelectorEntry:

    def fromXML(xml: Node): Try[AnimationSelectorEntry] = {
        for
            id <- Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
            variants <- (xml \ "variants" \ "AnimationSelectorVariant").map(AnimationSelectorVariantEntry.fromXML).invertTry
        yield
            AnimationSelectorEntry(id, variants)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("AnimationSelectorEntry", e.getMessage))
    }
