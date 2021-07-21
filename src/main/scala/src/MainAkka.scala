package src

import akka.actor.typed.ActorSystem
import src.actor.GameActor

object MainAkka:

    @main
    def runAkka(): Unit =
//        val gameActor: ActorSystem[GameActor.Command] = ActorSystem(GameActor(), "game-actor")

        Thread.sleep(2000)

//        gameActor ! GameActor.Shutdown

