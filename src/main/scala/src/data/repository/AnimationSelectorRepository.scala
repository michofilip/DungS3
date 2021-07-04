package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.{AnimationSelectorEntry, AnimationSelectorV2Entry, PhysicsSelectorV2Entry}
import src.game.entity.selector.AnimationSelector

import scala.xml.XML

class AnimationSelectorRepository(using animationRepository: AnimationRepository) extends Repository[Int, AnimationSelector] :

    //    override protected val dataById: Map[Int, AnimationSelector] =
    //        def convertToAnimationSelector(animationSelectorEntries: Seq[AnimationSelectorEntry]): AnimationSelector = {
    //            val animations = for {
    //                animationSelectorEntry <- animationSelectorEntries
    //                animation <- animationRepository.findById(animationSelectorEntry.animationId)
    //            } yield {
    //                (animationSelectorEntry.state, animationSelectorEntry.direction) -> animation
    //            }
    //
    //            AnimationSelector(animations)
    //        }
    //
    //        FileReader.readFile(Resources.animationSelectorEntriesFile, AnimationSelectorEntry.reader)
    //            .groupBy(_.id)
    //            .view
    //            .mapValues(convertToAnimationSelector)
    //            .toMap

    override protected val dataById: Map[Int, AnimationSelector] =
        def convertToAnimationSelector(animationSelectorEntry: AnimationSelectorV2Entry): AnimationSelector = {
            val animations = for {
                singleAnimationSelectorEntry <- animationSelectorEntry.singleAnimationSelectorEntries
                animation <- animationRepository.findById(singleAnimationSelectorEntry.animationId)
            } yield {
                (singleAnimationSelectorEntry.state, singleAnimationSelectorEntry.direction) -> animation
            }

            AnimationSelector(animations)
        }

        val xml = XML.loadFile(Resources.animationSelectorEntriesXXmlFile)

        (xml \ "AnimationSelectorEntry")
            .flatMap(AnimationSelectorV2Entry.fromXML)
            .map(animationSelectorEntry => animationSelectorEntry.id -> convertToAnimationSelector(animationSelectorEntry))
            .toMap
