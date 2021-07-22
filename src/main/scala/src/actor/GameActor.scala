package src.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import src.actor.GameActor.{Command, GameStateCommand, Setup, Shutdown}
import src.actor.GameStateActor
import src.game.service.Engine

private class GameActor(gameFrameActor: ActorRef[GameStateActor.Command], context: ActorContext[Command]):

    private def behavior(setup: Setup): Behavior[Command] = Behaviors.receiveMessage {
        case Shutdown =>
            context.log.info(s"shutting down ${context.self.toString}")
            Behaviors.stopped

        case GameStateCommand(command) =>
            gameFrameActor ! command
            Behaviors.same
    }

object GameActor:

    sealed trait Command

    case object Shutdown extends Command

    final case class GameStateCommand(command: GameStateActor.Command) extends Command

    private final case class Setup()

    def apply(engine: Engine): Behavior[Command] = Behaviors.setup { context =>
        context.log.info(s"strating up ${context.self.toString}")

        val gameStateActor = context.spawn(GameStateActor(engine), "GameStateActor")

        val setup = Setup()

        new GameActor(gameStateActor, context)
            .behavior(setup)
    }
