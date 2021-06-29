package src.game.temporal

class Timer private(private val initialTimestamp: Timestamp,
                    private val maybeLastStartTimestamp: Option[Timestamp]):

    def running: Boolean = maybeLastStartTimestamp.isDefined

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
        maybeLastStartTimestamp.fold(initialTimestamp) { lastStartTimestamp =>
            initialTimestamp + Duration.durationBetween(lastStartTimestamp, Timestamp.now)
        }

    def durationSince(timestamp: Timestamp): Duration =
        Duration.durationBetween(timestamp, this.timestamp)

    override def toString: String = s"Timer(timestamp=$timestamp, running=$running)"

object Timer:
    def apply(initialTimestamp: Timestamp = Timestamp.zero, running: Boolean = false): Timer =
        if running then
            new Timer(initialTimestamp, Option(Timestamp.now))
        else
            new Timer(initialTimestamp, None)
