package src.data.file

import java.io.File
import scala.io.{BufferedSource, Source}
import scala.xml.XML

object Resources:

    val physics = Source.fromResource("physics.xml")
    val physicsSelectors = Source.fromResource("physicsSelectors.xml")
    val frames = Source.fromResource("frames.xml")
    val animations = Source.fromResource("animations.xml")
    val animationSelectors = Source.fromResource("animationSelectors.xml")
    val entityPrototypes = Source.fromResource("entityPrototypes.xml")
