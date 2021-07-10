package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameActor.{Command, GameFrameCommand, Setup, Shutdown}

private class GameActor(gameFrameActor: ActorRef[GameFrameActor.Command], context: ActorContext[Command]):

    private def behavior(setup: Setup): Behavior[Command] = Behaviors.receiveMessage {
        case Shutdown =>
            context.log.info(s"shutting down ${context.self.toString}")
            Behaviors.stopped

        case GameFrameCommand(command) =>
            gameFrameActor ! command
            Behaviors.same
    }

object GameActor:

    sealed trait Command

    case object Shutdown extends Command

    final case class GameFrameCommand(command: GameFrameActor.Command) extends Command

    private final case class Setup()

    def apply(): Behavior[Command] = Behaviors.setup { context =>
        context.log.info(s"strating up ${context.self.toString}")

        val gameFrameActor = context.spawn(GameFrameActor(), "GameFrameActor")

        val setup = Setup()

        new GameActor(gameFrameActor, context)
            .behavior(setup)
    }
    