package src.game.temporal

class Duration private(private[temporal] val durationValue: Long):
    def unary_+ : Duration = this

    def unary_- : Duration = new Duration(-durationValue)

    def +(that: Duration): Duration = Duration(durationValue + that.durationValue)

    def -(that: Duration): Duration = Duration(durationValue - that.durationValue)

    override def toString: String = durationValue.toString

object Duration:
    def apply(durationValue: Long): Duration = new Duration(durationValue)

    def zero: Duration = Duration(0)

    def durationBetween(from: Timestamp, to: Timestamp): Duration = Duration(to.timestampValue - from.timestampValue)

    extension (durationValue: Long)
        def milliseconds: Duration = Duration(durationValue)

        def seconds: Duration = Duration(durationValue * 1000)

        def minutes: Duration = Duration(durationValue * 1000 * 60)

        def hours: Duration = Duration(durationValue * 1000 * 60 * 60)
