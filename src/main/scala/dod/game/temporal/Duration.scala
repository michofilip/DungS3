package dod.game.temporal

class Duration private(val milliseconds: Long):
    def unary_+ : Duration = this

    def unary_- : Duration = new Duration(-milliseconds)

    def +(that: Duration): Duration = Duration(milliseconds + that.milliseconds)

    def -(that: Duration): Duration = Duration(milliseconds - that.milliseconds)

    override def toString: String = milliseconds.toString

object Duration:
    def apply(milliseconds: Long): Duration = new Duration(milliseconds)

    def zero: Duration = Duration(0)

    def durationBetween(from: Timestamp, to: Timestamp): Duration = Duration(to.milliseconds - from.milliseconds)

    extension (milliseconds: Long)
        def milliseconds: Duration = Duration(milliseconds)

        def seconds: Duration = Duration(milliseconds * 1000)

        def minutes: Duration = Duration(milliseconds * 1000 * 60)

        def hours: Duration = Duration(milliseconds * 1000 * 60 * 60)

    extension (milliseconds: Double)
        def milliseconds: Duration = Duration(milliseconds.toLong)

        def seconds: Duration = Duration((milliseconds * 1000).toLong)

        def minutes: Duration = Duration((milliseconds * 1000 * 60).toLong)

        def hours: Duration = Duration((milliseconds * 1000 * 60 * 60).toLong)
