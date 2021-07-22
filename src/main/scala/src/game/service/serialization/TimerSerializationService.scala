package src.game.service.serialization

import src.game.temporal.{Timer, Timestamp}

import scala.util.Try
import scala.xml.Node

object TimerSerializationService:

    def toXml(timer: Timer): Node =
        <Timer>
            <timestamp> {timer.timestamp} </timestamp>
            <running> {timer.running} </running>
        </Timer>

    def fromXml(xml: Node): Option[Timer] = Try {
        Timer(
            initialTimestamp = Timestamp((xml \ "timestamp").text.trim.toLong),
            running = (xml \ "running").text.trim.toBoolean
        )
    }.toOption
