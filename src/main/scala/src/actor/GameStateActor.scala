package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameStateActor.{Command, DisplayGameState, ProcessGameState, SetDislayingEnbled, SetGameState, SetProcessingEnabled, Setup}
import src.actor.GameStateProcessorActor
import src.game.GameState

private class GameStateActor(gameStateProcessorActor: ActorRef[GameStateProcessorActor.Command],
                             gameStateDisplayActor: ActorRef[GameStateDisplayActor.Command],
                             context: ActorContext[Command]):

    private def behavior(setup: Setup): Behavior[Command] = Behaviors.receiveMessage {
        case SetProcessingEnabled(processingEnabled) =>
            behavior(setup.copy(processingEnabled = processingEnabled))

        case SetDislayingEnbled(dislayingEnbled) =>
            behavior(setup.copy(dislayingEnbled = dislayingEnbled))

        case SetGameState(gameState) =>
            behavior(setup.copy(gameState = Some(gameState)))

        case ProcessGameState =>
            setup.gameState match {
                case Some(gameState) if setup.processingEnabled => gameStateProcessorActor ! GameStateProcessorActor.Process(gameState)
                case _ => gameStateProcessorActor ! GameStateProcessorActor.Skip
            }
            Behaviors.same

        case DisplayGameState =>
            setup.gameState match {
                case Some(gameState) if setup.dislayingEnbled => gameStateDisplayActor ! GameStateDisplayActor.Display(gameState)
                case _ => gameStateDisplayActor ! GameStateDisplayActor.Skip
            }
            Behaviors.same

    }

object GameStateActor:

    sealed trait Command

    final case class SetProcessingEnabled(processingEnabled: Boolean) extends Command

    final case class SetDislayingEnbled(dislayingEnbled: Boolean) extends Command

    final case class SetGameState(gameState: GameState) extends Command

    private[actor] case object ProcessGameState extends Command

    private[actor] case object DisplayGameState extends Command

    private case class Setup(processingEnabled: Boolean = false,
                             dislayingEnbled: Boolean = false,
                             gameState: Option[GameState] = None)

    def apply(): Behavior[Command] = Behaviors.setup { context =>

        val gameFrameProcessorActor = context.spawn(GameStateProcessorActor(context.self), "GameStateProcessorActor")
        val gameStateDisplayActor = context.spawn(GameStateDisplayActor(context.self), "GameStateDisplayActor")

        val setup = Setup()

        new GameStateActor(gameFrameProcessorActor, gameStateDisplayActor, context)
            .behavior(setup)
    }
