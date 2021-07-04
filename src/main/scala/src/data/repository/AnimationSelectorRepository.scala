package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.{AnimationSelectorEntry, AnimationSelectorV2Entry, PhysicsSelectorV2Entry}
import src.game.entity.selector.AnimationSelector

import scala.xml.XML

class AnimationSelectorRepository(using animationRepository: AnimationRepository) extends Repository[Int, AnimationSelector] :

    override protected val dataById: Map[Int, AnimationSelector] =
        def convertToAnimationSelector(animationSelectorEntry: AnimationSelectorV2Entry): AnimationSelector = {
            val animations = for {
                variant <- animationSelectorEntry.variants
                animation <- animationRepository.findById(variant.animationId)
            } yield {
                (variant.state, variant.direction) -> animation
            }

            AnimationSelector(animations)
        }

        val xml = XML.loadFile(Resources.animationSelectorsFile)

        (xml \ "AnimationSelector")
            .flatMap(AnimationSelectorV2Entry.fromXML)
            .map(animationSelectorEntry => animationSelectorEntry.id -> convertToAnimationSelector(animationSelectorEntry))
            .toMap
