package src.data.model

import src.data.file.FileReader.Reader

import scala.util.Try

case class PhysicsEntry(id: Int, solid: Boolean, opaque: Boolean)

object PhysicsEntry:
    
    val reader: Reader[PhysicsEntry] = strArr => Try {
        val id = strArr(0).toInt
        val solid = strArr(1) == "1"
        val opaque = strArr(2) == "1"

        PhysicsEntry(id = id, solid = solid, opaque = opaque)
    }.toOption
