package src.data.model

import src.exception.FailedToReadObject
import src.game.entity.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class PhysicsSelectorVariantEntry(state: Option[State], physicsId: Int)

object PhysicsSelectorVariantEntry:

    def fromXML(xml: Node): Try[PhysicsSelectorVariantEntry] =
        val state = Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
        val physicsId = Try((xml \ "physicsId").map(_.text.trim).map(_.toInt).head)

        {
            for
                state <- state
                physicsId <- physicsId
            yield
                PhysicsSelectorVariantEntry(state, physicsId)
        }.recoverWith {
            case e => Failure(new FailedToReadObject("PhysicsSelectorVariantEntry", e.getMessage))
        }
