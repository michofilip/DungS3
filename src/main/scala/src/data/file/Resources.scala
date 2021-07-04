package src.data.file

import java.io.File
import scala.io.{BufferedSource, Source}
import scala.xml.XML

object Resources:

    val physicsFile = File("src/main/resources/physicsEntries.xml")
    val physicsSelectorsFile = File("src/main/resources/physicsSelectorEntries.xml")
    val framesFile = File("src/main/resources/frameEntries.xml")
    val animationsFile = File("src/main/resources/animationEntries.xml")
    val animationSelectorsFile = File("src/main/resources/animationSelectorEntries.xml")
    val entityPrototypesFile = File("src/main/resources/entityPrototypeEntries.xml")
