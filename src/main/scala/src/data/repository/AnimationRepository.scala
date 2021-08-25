package src.data.repository

import src.data.Resources
import src.data.model.AnimationEntry
import src.game.entity.parts.graphics.Animation
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class AnimationRepository private(frameRepository: FrameRepository) extends Repository[Int, Animation] :

    override protected val dataById: Map[Int, Animation] =
        def animationFrom(animationEntry: AnimationEntry): Try[Animation] =
            animationEntry.frameIds.map { frameId =>
                frameRepository.findById(frameId).toTry(new NoSuchElementException(s"Frame id: $frameId not found!"))
            }.invertTry.map { frames =>
                Animation(
                    fps = animationEntry.fps,
                    frames = frames.toIndexedSeq,
                    looping = animationEntry.looping
                )
            }

        val xml = XML.load(Resources.animations.reader())

        (xml \ "Animation").map { node =>
            for
                animationEntry <- AnimationEntry.fromXML(node)
                animation <- animationFrom(animationEntry)
            yield
                animationEntry.id -> animation
        }.invertTry.map(_.toMap).get

object AnimationRepository:

    private lazy val animationRepository = new AnimationRepository(FrameRepository())

    def apply(): AnimationRepository = animationRepository
    