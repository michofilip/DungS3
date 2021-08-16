package src.data.model

import src.utils.TryUtils.*

import scala.util.Try
import scala.xml.Node

final case class AnimationSelectorEntry(id: Int, variants: Seq[AnimationSelectorVariantEntry])

object AnimationSelectorEntry:

    def fromXML(xml: Node): Try[AnimationSelectorEntry] =
        val id = Try((xml \ "id").map(_.text.trim).map(_.toInt).head)
        val variants = (xml \ "variants" \ "AnimationSelectorVariant")
            .map(AnimationSelectorVariantEntry.fromXML)
            .invertTry

        for {
            id <- id
            variants <- variants
        } yield {
            AnimationSelectorEntry(id, variants)
        }
        