package src.data.repository

import src.data.Resources
import src.data.model.AnimationSelectorEntity
import src.game.gameobject.parts.graphics.AnimationSelector
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class AnimationSelectorRepository private(animationRepository: AnimationRepository) extends Repository[Int, AnimationSelector] :

    override protected val dataById: Map[Int, AnimationSelector] =
        def animationSelectorFrom(animationSelectorEntity: AnimationSelectorEntity): Try[AnimationSelector] =
            val animations = animationSelectorEntity.variants.map { variant =>
                animationRepository.findById(variant.animationId).map { animation =>
                    (variant.state, variant.direction) -> animation
                }.toTry {
                    new NoSuchElementException(s"Animation id: ${variant.animationId} not found!")
                }
            }.toTrySeq

            for
                animations <- animations
            yield
                AnimationSelector(animations)

        val xml = XML.load(Resources.animationSelectors.reader())

        (xml \ "AnimationSelector").map { node =>
            for
                animationSelectorEntity <- AnimationSelectorEntity.fromXML(node)
                animationSelector <- animationSelectorFrom(animationSelectorEntity)
            yield
                animationSelectorEntity.id -> animationSelector
        }.toTrySeq.map(_.toMap).get

object AnimationSelectorRepository:

    private lazy val animationSelectorRepository = new AnimationSelectorRepository(AnimationRepository())

    def apply(): AnimationSelectorRepository = animationSelectorRepository
