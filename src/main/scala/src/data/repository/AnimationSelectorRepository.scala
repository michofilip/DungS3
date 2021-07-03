package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.AnimationSelectorEntry
import src.game.entity.selector.AnimationSelector

class AnimationSelectorRepository(using animationRepository: AnimationRepository) extends Repository[Int, AnimationSelector] :

    override protected val dataById: Map[Int, AnimationSelector] =
        def convertToAnimationSelector(animationSelectorEntries: Seq[AnimationSelectorEntry]): AnimationSelector = {
            val animations = for {
                animationSelectorEntry <- animationSelectorEntries
                animation <- animationRepository.findById(animationSelectorEntry.animationId)
            } yield {
                (animationSelectorEntry.state, animationSelectorEntry.direction) -> animation
            }

            AnimationSelector(animations)
        }

        FileReader.readFile(Resources.animationSelectorEntriesFile, AnimationSelectorEntry.reader)
            .groupBy(_.id)
            .view
            .mapValues(convertToAnimationSelector)
            .toMap
        