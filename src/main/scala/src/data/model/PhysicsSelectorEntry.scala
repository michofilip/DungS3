package src.data.model

import src.data.file.FileReader.Reader
import src.game.entity.parts.State

import scala.util.Try

case class PhysicsSelectorEntry(id: Int, state: Option[State], physicsId: Int)

object PhysicsSelectorEntry:

    val reader: Reader[PhysicsSelectorEntry] = strArr => Try {
        val id = strArr(0).toInt
        val state = if strArr(1).nonEmpty then Some(State.valueOf(strArr(1))) else None
        val physicsId = strArr(2).toInt

        PhysicsSelectorEntry(id = id, state = state, physicsId = physicsId)
    }.toOption
