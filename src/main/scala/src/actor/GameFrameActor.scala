package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameFrameActor.{Command, DisplayGameState, ProcessGameFrame, SetDislayingEnbled, SetGameFrame, SetProcessingEnabled, Setup}
import src.actor.GameFrameProcessorActor
import src.game.GameFrame

private class GameFrameActor(gameFrameProcessorActor: ActorRef[GameFrameProcessorActor.Command],
                             gameStateDisplayActor: ActorRef[GameStateDisplayActor.Command],
                             context: ActorContext[Command]):

    private def behavior(setup: Setup): Behavior[Command] = Behaviors.receiveMessage {
        case SetProcessingEnabled(processingEnabled) =>
            behavior(setup.copy(processingEnabled = processingEnabled))

        case SetDislayingEnbled(dislayingEnbled) =>
            behavior(setup.copy(dislayingEnbled = dislayingEnbled))

        case SetGameFrame(gameFrame) =>
            behavior(setup.copy(gameFrame = Some(gameFrame)))

        case ProcessGameFrame =>
            setup.gameFrame match {
                case Some(gameFrame) if setup.processingEnabled => gameFrameProcessorActor ! GameFrameProcessorActor.Process(gameFrame)
                case _ => gameFrameProcessorActor ! GameFrameProcessorActor.Skip
            }
            Behaviors.same

        case DisplayGameState =>
            setup.gameFrame match {
                case Some(gameFrame) if setup.dislayingEnbled => gameStateDisplayActor ! GameStateDisplayActor.Display(gameFrame.gameState)
                case _ => gameStateDisplayActor ! GameStateDisplayActor.Skip
            }
            Behaviors.same

    }

object GameFrameActor:

    sealed trait Command

    final case class SetProcessingEnabled(processingEnabled: Boolean) extends Command

    final case class SetDislayingEnbled(dislayingEnbled: Boolean) extends Command

    final case class SetGameFrame(gameFrame: GameFrame) extends Command

    private[actor] case object ProcessGameFrame extends Command

    private[actor] case object DisplayGameState extends Command

    private case class Setup(processingEnabled: Boolean = false,
                             dislayingEnbled: Boolean = false,
                             gameFrame: Option[GameFrame] = None)

    def apply(): Behavior[Command] = Behaviors.setup { context =>

        val gameFrameProcessorActor = context.spawn(GameFrameProcessorActor(context.self), "GameFrameProcessorActor")
        val gameStateDisplayActor = context.spawn(GameStateDisplayActor(context.self), "GameStateDisplayActor")

        val setup = Setup()

        new GameFrameActor(gameFrameProcessorActor, gameStateDisplayActor, context)
            .behavior(setup)
    }
