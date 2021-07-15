package src.data.model

import scala.util.Try
import scala.xml.Node

final case class AnimationSelectorEntry(id: Int, variants: Seq[AnimationSelectorVariantEntry])

object AnimationSelectorEntry:

    def fromXML(xml: Node): Option[AnimationSelectorEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val singleAnimationSelectorEntries = (xml \ "variants" \ "AnimationSelectorVariant")
            .flatMap(AnimationSelectorVariantEntry.fromXML)

        AnimationSelectorEntry(id, singleAnimationSelectorEntries)
    }.toOption