package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameFrameProcessorActor.{Command, Process, Skip}
import src.game.GameFrame

private class GameFrameProcessorActor(gameFrameActor: ActorRef[GameFrameActor.Command], context: ActorContext[Command]):

    gameFrameActor ! GameFrameActor.ProcessGameFrame

    private def behavior: Behavior[Command] = Behaviors.receiveMessage {
        case Skip =>
            gameFrameActor ! GameFrameActor.ProcessGameFrame
            Behaviors.same

        case Process(gameFrame) =>
            val nextGameFrame = gameFrame.nextFrame
            gameFrameActor ! GameFrameActor.SetGameFrame(nextGameFrame)
            gameFrameActor ! GameFrameActor.ProcessGameFrame
            Behaviors.same
    }

object GameFrameProcessorActor:

    sealed trait Command

    final case class Process(gameFrame: GameFrame) extends Command

    case object Skip extends Command

    def apply(gameFrameActor: ActorRef[GameFrameActor.Command]): Behavior[Command] = Behaviors.setup { context =>
        new GameFrameProcessorActor(gameFrameActor, context).behavior
    }
