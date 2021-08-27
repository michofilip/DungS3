package dod.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import dod.actor.GameStateDisplayActor.{Command, Display, Skip}
import dod.game.GameState

private class GameStateDisplayActor(gameStateActor: ActorRef[GameStateActor.Command],
                                    context: ActorContext[Command]):

    gameStateActor ! GameStateActor.DisplayGameState

    private def behavior: Behavior[Command] = Behaviors.receiveMessage {
        case Skip =>
            gameStateActor ! GameStateActor.DisplayGameState
            Behaviors.same

        case Display(gameState) =>
            context.log.info("Displaying gameState")
            gameStateActor ! GameStateActor.DisplayGameState
            Behaviors.same
    }

object GameStateDisplayActor:

    sealed trait Command

    final case class Display(gameState: GameState) extends Command

    case object Skip extends Command

    def apply(gameStateActor: ActorRef[GameStateActor.Command]): Behavior[Command] = Behaviors.setup { conrext =>

        new GameStateDisplayActor(gameStateActor, conrext).behavior
    }


