package src.data

import java.io.File
import scala.io.Source

object Resources:

    val physics = Source.fromResource("physics.xml")
    val physicsSelectors = Source.fromResource("physicsSelectors.xml")
    val frames = Source.fromResource("frames.xml")
    val animations = Source.fromResource("animations.xml")
    val animationSelectors = Source.fromResource("animationSelectors.xml")
    val gameObjectPrototypes = Source.fromResource("gameObjectPrototypes.xml")


    val tileSets = "src/main/resources/tileSets"

    val sprites = new File("src/main/resources/sprites.xml") 
