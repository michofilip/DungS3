package src.game.temporal

import scala.util.Try
import scala.xml.Node

class Timer private(private val initialTimestamp: Timestamp,
                    private val maybeLastStartTimestamp: Option[Timestamp],
                    private val previousDuration: Duration):

    def running: Boolean = maybeLastStartTimestamp.isDefined

    def started: Timer =
        if running then
            this
        else
            new Timer(initialTimestamp, Option(Timestamp.now), previousDuration)

    def stopped: Timer =
        if running then
            new Timer(timestamp, None, duration)
        else
            this

    def timestamp: Timestamp =
        maybeLastStartTimestamp.fold(initialTimestamp) { lastStartTimestamp =>
            initialTimestamp + Duration.durationBetween(lastStartTimestamp, Timestamp.now)
        }

    def duration: Duration =
        previousDuration + Duration.durationBetween(initialTimestamp, timestamp)

    def durationSince(timestamp: Timestamp): Duration =
        Duration.durationBetween(timestamp, this.timestamp)

    override def toString: String = s"Timer(timestamp=$timestamp, duration=$duration, running=$running)"

    def toXml: Node =
        <Timer>
            <timestamp> {timestamp} </timestamp>
            <duration> {duration} </duration>
            <running> {running} </running>
        </Timer>

object Timer:

    def apply(initialTimestamp: Timestamp = Timestamp.zero, previousDuration: Duration = Duration.zero, running: Boolean = false): Timer =
        if running then
            new Timer(initialTimestamp, Option(Timestamp.now), previousDuration)
        else
            new Timer(initialTimestamp, None, previousDuration)

    def fromXml(xml: Node): Option[Timer] = Try {
        Timer(
            initialTimestamp = Timestamp((xml \ "timestamp").text.trim.toLong),
            previousDuration = Duration((xml \ "duration").text.trim.toLong),
            running = (xml \ "running").text.trim.toBoolean
        )
    }.toOption
        
