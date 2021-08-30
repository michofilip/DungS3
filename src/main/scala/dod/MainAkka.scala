package dod

import akka.actor.typed.ActorSystem
import dod.actor.GameActor

object MainAkka:

    @main
    def runAkka(): Unit =
        val gameActor: ActorSystem[GameActor.Command] = ActorSystem(GameActor(), "gameActor")

        Thread.sleep(2000)

        gameActor ! GameActor.Shutdown

