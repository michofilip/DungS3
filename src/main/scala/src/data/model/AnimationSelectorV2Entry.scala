package src.data.model

import scala.util.Try
import scala.xml.Node

case class AnimationSelectorV2Entry(id: Int, singleAnimationSelectorEntries: Seq[SingleAnimationSelectorEntry])

object AnimationSelectorV2Entry:

    def fromXML(xml: Node): Option[AnimationSelectorV2Entry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val singleAnimationSelectorEntries = (xml \ "singleAnimationSelectorEntries" \ "SingleAnimationSelectorEntry")
            .flatMap(SingleAnimationSelectorEntry.fromXML)

        AnimationSelectorV2Entry(id, singleAnimationSelectorEntries)
    }.toOption