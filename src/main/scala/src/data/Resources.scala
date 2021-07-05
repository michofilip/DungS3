package src.data

import scala.io.Source

object Resources:

    val physics = Source.fromResource("physics.xml")
    val physicsSelectors = Source.fromResource("physicsSelectors.xml")
    val frames = Source.fromResource("frames.xml")
    val animations = Source.fromResource("animations.xml")
    val animationSelectors = Source.fromResource("animationSelectors.xml")
    val entityPrototypes = Source.fromResource("entityPrototypes.xml")
