package src.data.file

import java.io.File
import scala.io.{BufferedSource, Source}
import scala.xml.XML

object Resources:

    val physicsFile = File("src/main/resources/physics.xml")
    val physicsSelectorsFile = File("src/main/resources/physicsSelectors.xml")
    val framesFile = File("src/main/resources/frames.xml")
    val animationsFile = File("src/main/resources/animations.xml")
    val animationSelectorsFile = File("src/main/resources/animationSelectors.xml")
    val entityPrototypesFile = File("src/main/resources/entityPrototypes.xml")
