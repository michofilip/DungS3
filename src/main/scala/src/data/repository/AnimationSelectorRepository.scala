package src.data.repository

import src.data.Resources
import src.data.model.{AnimationSelectorEntry, PhysicsSelectorEntry}
import src.game.entity.selector.AnimationSelector

import scala.xml.XML

class AnimationSelectorRepository(animationRepository: AnimationRepository) extends Repository[Int, AnimationSelector] :

    override protected val dataById: Map[Int, AnimationSelector] =
        def convertToAnimationSelector(animationSelectorEntry: AnimationSelectorEntry): AnimationSelector = {
            val animations = for {
                variant <- animationSelectorEntry.variants
                animation <- animationRepository.findById(variant.animationId)
            } yield {
                (variant.state, variant.direction) -> animation
            }

            AnimationSelector(animations)
        }

        val xml = XML.load(Resources.animationSelectors.reader())

        (xml \ "AnimationSelector")
            .flatMap(AnimationSelectorEntry.fromXML)
            .map(animationSelectorEntry => animationSelectorEntry.id -> convertToAnimationSelector(animationSelectorEntry))
            .toMap
