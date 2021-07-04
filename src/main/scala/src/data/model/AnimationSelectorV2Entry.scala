package src.data.model

import scala.util.Try
import scala.xml.Node

case class AnimationSelectorV2Entry(id: Int, variants: Seq[AnimationSelectorVariantEntry])

object AnimationSelectorV2Entry:

    def fromXML(xml: Node): Option[AnimationSelectorV2Entry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val singleAnimationSelectorEntries = (xml \ "variants" \ "AnimationSelectorVariant")
            .flatMap(AnimationSelectorVariantEntry.fromXML)

        AnimationSelectorV2Entry(id, singleAnimationSelectorEntries)
    }.toOption