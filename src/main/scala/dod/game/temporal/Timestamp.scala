package dod.game.temporal

class Timestamp private(val milliseconds: Long):
    def +(duration: Duration): Timestamp = Timestamp(milliseconds + duration.milliseconds)

    def -(duration: Duration): Timestamp = Timestamp(milliseconds - duration.milliseconds)

    override def toString: String = milliseconds.toString

object Timestamp:
    def apply(milliseconds: Long): Timestamp = new Timestamp(milliseconds)

    def zero: Timestamp = Timestamp(0)

    def now: Timestamp = Timestamp(System.currentTimeMillis())
