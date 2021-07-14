package src.game.temporal

import scala.util.Try
import scala.xml.Node

class Timer private(private val initialTimestamp: Timestamp,
                    private val lastStartTimestamp: Option[Timestamp]):

    def running: Boolean = lastStartTimestamp.isDefined

    def started: Timer =
        if running then
            this
        else
            new Timer(initialTimestamp, Option(Timestamp.now))

    def stopped: Timer =
        if running then
            new Timer(timestamp, None)
        else
            this

    def timestamp: Timestamp =
        lastStartTimestamp.fold(initialTimestamp) { lastStartTimestamp =>
            initialTimestamp + Duration.durationBetween(lastStartTimestamp, Timestamp.now)
        }

    def duration: Duration = Duration.durationBetween(Timestamp.zero, timestamp)

    def durationSince(timestamp: Timestamp): Duration =
        Duration.durationBetween(timestamp, this.timestamp)

    override def toString: String = s"Timer(timestamp=$timestamp, running=$running)"

    def toXml: Node =
        <Timer>
            <timestamp> {timestamp} </timestamp>
            <running> {running} </running>
        </Timer>

object Timer:

    def apply(initialTimestamp: Timestamp = Timestamp.zero, running: Boolean = false): Timer =
        if running then
            new Timer(initialTimestamp, Option(Timestamp.now))
        else
            new Timer(initialTimestamp, None)

    def fromXml(xml: Node): Option[Timer] = Try {
        Timer(
            initialTimestamp = Timestamp((xml \ "timestamp").text.trim.toLong),
            running = (xml \ "running").text.trim.toBoolean
        )
    }.toOption
        
