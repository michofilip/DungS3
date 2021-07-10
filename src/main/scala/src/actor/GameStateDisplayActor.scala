package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameStateDisplayActor.{Command, Display, Skip}
import src.game.GameState

private class GameStateDisplayActor(gameFrameActor: ActorRef[GameFrameActor.Command],
                                    context: ActorContext[Command]):

    gameFrameActor ! GameFrameActor.DisplayGameState

    private def behavior: Behavior[Command] = Behaviors.receiveMessage {
        case Skip =>
            gameFrameActor ! GameFrameActor.DisplayGameState
            Behaviors.same

        case Display(gameState) =>
            context.log.info("Displaying gameState")
            gameFrameActor ! GameFrameActor.DisplayGameState
            Behaviors.same
    }

object GameStateDisplayActor:

    sealed trait Command

    final case class Display(gameState: GameState) extends Command

    case object Skip extends Command

    def apply(gameFrameActor: ActorRef[GameFrameActor.Command]): Behavior[Command] = Behaviors.setup { conrext =>

        new GameStateDisplayActor(gameFrameActor, conrext).behavior
    }


