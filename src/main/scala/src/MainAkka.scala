package src

import akka.actor.typed.ActorSystem
import src.actor.GameActor
import src.game.service.Engine

object MainAkka:

    @main
    def runAkka(): Unit =
        val engine = new Engine
        val gameActor: ActorSystem[GameActor.Command] = ActorSystem(GameActor(engine), "gameActor")

        Thread.sleep(2000)

        gameActor ! GameActor.Shutdown

