package src.data.repository

import src.data.Resources
import src.data.model.AnimationEntry
import src.game.gameobject.parts.graphics.Animation
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class AnimationRepository private(frameRepository: FrameRepository) extends Repository[Int, Animation] :

    override protected val dataById: Map[Int, Animation] =
        def animationFrom(animationEntry: AnimationEntry): Try[Animation] =
            val frames = animationEntry.frameIds.map { frameId =>
                frameRepository.findById(frameId).toTry {
                    new NoSuchElementException(s"Frame id: $frameId not found!")
                }
            }.toTrySeq

            for
                frames <- frames
            yield
                Animation(
                    fps = animationEntry.fps,
                    frames = frames,
                    looping = animationEntry.looping
                )

        val xml = XML.load(Resources.animations.reader())

        (xml \ "Animation").map { node =>
            for
                animationEntry <- AnimationEntry.fromXML(node)
                animation <- animationFrom(animationEntry)
            yield
                animationEntry.id -> animation
        }.toTrySeq.map(_.toMap).get

object AnimationRepository:

    private lazy val animationRepository = new AnimationRepository(FrameRepository())

    def apply(): AnimationRepository = animationRepository
    