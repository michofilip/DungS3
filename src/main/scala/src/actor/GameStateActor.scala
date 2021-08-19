package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameStateActor.{AddEvents, Command, DisplayGameState, ProcessGameState, SetDisplayingEnabled, SetGameState, SetProcessingEnabled, Setup}
import src.actor.GameStateProcessorActor
import src.game.GameState
import src.game.event.Event

private class GameStateActor(gameStateProcessorActor: ActorRef[GameStateProcessorActor.Command],
                             gameStateDisplayActor: ActorRef[GameStateDisplayActor.Command],
                             context: ActorContext[Command]):

    private def behavior(setup: Setup): Behavior[Command] = Behaviors.receiveMessage {
        case SetProcessingEnabled(processingEnabled) =>
            behavior(setup.copy(processingEnabled = processingEnabled))

        case SetDisplayingEnabled(displayingEnabled) =>
            behavior(setup.copy(displayingEnabled = displayingEnabled))

        case SetGameState(gameState) =>
            behavior(setup.copy(gameState = Some(gameState)))

        case AddEvents(events) =>
            setup.gameState match 
                case Some(gameState) =>
                    behavior(setup.copy(gameState = Some(gameState.addEvents(events))))
                case _ =>
                    Behaviors.same

        case ProcessGameState =>
            setup.gameState match 
                case Some(gameState) if setup.processingEnabled =>
                    gameStateProcessorActor ! GameStateProcessorActor.Process(gameState)
                case _ =>
                    gameStateProcessorActor ! GameStateProcessorActor.Skip
            Behaviors.same

        case DisplayGameState =>
            setup.gameState match 
                case Some(gameState) if setup.displayingEnabled =>
                    gameStateDisplayActor ! GameStateDisplayActor.Display(gameState)
                case _ =>
                    gameStateDisplayActor ! GameStateDisplayActor.Skip
            Behaviors.same

    }

object GameStateActor:

    sealed trait Command

    final case class SetProcessingEnabled(processingEnabled: Boolean) extends Command

    final case class SetDisplayingEnabled(displayingEnabled: Boolean) extends Command

    final case class SetGameState(gameState: GameState) extends Command

    final case class AddEvents(events: Seq[Event]) extends Command

    private[actor] case object ProcessGameState extends Command

    private[actor] case object DisplayGameState extends Command

    private case class Setup(processingEnabled: Boolean = false,
                             displayingEnabled: Boolean = false,
                             gameState: Option[GameState] = None)

    def apply(): Behavior[Command] = Behaviors.setup { context =>

        val gameFrameProcessorActor = context.spawn(GameStateProcessorActor(context.self), "GameStateProcessorActor")
        val gameStateDisplayActor = context.spawn(GameStateDisplayActor(context.self), "GameStateDisplayActor")

        new GameStateActor(gameFrameProcessorActor, gameStateDisplayActor, context)
            .behavior(Setup())
    }
