package src.data.model

import src.data.file.FileReader.{Reader, *}
import src.game.entity.parts.State

import scala.util.Try

case class PhysicsSelectorEntry(id: Int, state: Option[State], physicsId: Int)

object PhysicsSelectorEntry:

    val reader: Reader[PhysicsSelectorEntry] = strArr => Try {
        val id = strArr(0).asInt
        val state = strArr(1).asOption(State.valueOf)
        val physicsId = strArr(2).asInt

        PhysicsSelectorEntry(id = id, state = state, physicsId = physicsId)
    }.toOption
