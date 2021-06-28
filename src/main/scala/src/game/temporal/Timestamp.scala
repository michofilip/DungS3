package src.game.temporal

class Timestamp private(private[temporal] val timestampValue: Long):
    def +(duration: Duration): Timestamp = Timestamp(timestampValue + duration.durationValue)

    def -(duration: Duration): Timestamp = Timestamp(timestampValue - duration.durationValue)

    override def toString: String = timestampValue.toString

object Timestamp:
    def apply(timestampValue: Long): Timestamp = new Timestamp(timestampValue)

    def zero: Timestamp = Timestamp(0)

    def now: Timestamp = Timestamp(System.currentTimeMillis())
