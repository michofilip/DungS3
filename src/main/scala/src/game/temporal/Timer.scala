package src.game.temporal

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

    override def toString: String = s"Timer(timestamp=$timestamp, running=$running)"

object Timer:
    def apply(initialTimestamp: Timestamp = Timestamp.zero, previousDuration: Duration = Duration.zero, running: Boolean = false): Timer =
        if running then
            new Timer(initialTimestamp, Option(Timestamp.now), previousDuration)
        else
            new Timer(initialTimestamp, None, previousDuration)
