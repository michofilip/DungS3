package src

import src.game.service.{GameStateBuilder, GameStateFileProcessor}

import java.io.File

object MainMapLoader:

    @main
    def run(): Unit =
        val gs0 = GameStateBuilder().load("map1")

        gs0.foreach { gameState =>
            GameStateFileProcessor().saveToFile(File("gameState4.xml"), gameState)
        }
        GameStateFileProcessor().loadFromFile(File("gameState4.xml")).foreach(println)

