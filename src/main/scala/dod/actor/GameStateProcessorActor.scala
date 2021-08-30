package dod.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import dod.actor.GameStateProcessorActor.{Command, Process, Skip}
import dod.game.GameState
import dod.game.service.GameStateProcessor

private class GameStateProcessorActor(gameStateActor: ActorRef[GameStateActor.Command],
                                      gameStateProcessor: GameStateProcessor,
                                      context: ActorContext[Command]):

    gameStateActor ! GameStateActor.ProcessGameState

    private def behavior: Behavior[Command] = Behaviors.receiveMessage {
        case Skip =>
            gameStateActor ! GameStateActor.ProcessGameState
            Behaviors.same

        case Process(gameState) =>
            val nextGameState = gameStateProcessor.processNextEvent(gameState)
            gameStateActor ! GameStateActor.SetGameState(nextGameState)
            gameStateActor ! GameStateActor.ProcessGameState
            Behaviors.same
    }

object GameStateProcessorActor:

    sealed trait Command

    final case class Process(gameState: GameState) extends Command

    case object Skip extends Command

    def apply(gameStateActor: ActorRef[GameStateActor.Command]): Behavior[Command] = Behaviors.setup { context =>
        new GameStateProcessorActor(gameStateActor, GameStateProcessor(), context).behavior
    }
