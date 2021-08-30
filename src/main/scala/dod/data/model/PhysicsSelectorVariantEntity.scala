package dod.data.model

import dod.exception.FailedToReadObject
import dod.game.gameobject.parts.state.State

import scala.util.{Failure, Try}
import scala.xml.Node

final case class PhysicsSelectorVariantEntity(state: Option[State], physicsId: Int)

object PhysicsSelectorVariantEntity:

    def fromXML(xml: Node): Try[PhysicsSelectorVariantEntity] = {
        for
            state <- Try((xml \ "state").map(_.text.trim).map(State.valueOf).headOption)
            physicsId <- Try((xml \ "physicsId").map(_.text.trim).map(_.toInt).head)
        yield
            PhysicsSelectorVariantEntity(state, physicsId)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("PhysicsSelectorVariantEntity", e.getMessage))
    }
