package src.game.service.serialization

import src.exception.FailedToReadObject
import src.game.temporal.{Timer, Timestamp}

import scala.util.{Failure, Try}
import scala.xml.Node

object TimerSerializationService:

    def toXml(timer: Timer): Node =
        <Timer>
            <timestamp> {timer.timestamp} </timestamp>
            <running> {timer.running} </running>
        </Timer>

    def fromXml(xml: Node): Try[Timer] =
        val initialTimestamp = Try((xml \ "timestamp").map(_.text.trim).map(_.toLong).map(Timestamp.apply).head)
        val running = Try((xml \ "running").map(_.text.trim).map(_.toBoolean).head)

        {
            for
                initialTimestamp <- initialTimestamp
                running <- running
            yield
                Timer(initialTimestamp, running)
        }.recoverWith {
            case e => Failure(new FailedToReadObject("Timer", e.getMessage))
        }
