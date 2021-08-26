package src.data.model

import src.exception.FailedToReadObject
import src.game.gameobject.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class PhysicsSelectorVariantEntry(state: Option[State], physicsId: Int)

object PhysicsSelectorVariantEntry:

    def fromXML(xml: Node): Try[PhysicsSelectorVariantEntry] = {
        for
            state <- Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
            physicsId <- Try((xml \ "physicsId").map(_.text.trim).map(_.toInt).head)
        yield
            PhysicsSelectorVariantEntry(state, physicsId)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("PhysicsSelectorVariantEntry", e.getMessage))
    }
