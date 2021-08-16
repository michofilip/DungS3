package src.utils

import scala.util.{Failure, Success, Try}

object TryUtils:
    private def invertTry[T](seq: Seq[Try[T]]): Try[Seq[T]] =
        def inv(seq: Seq[Try[T]], agg: Seq[T]): Try[Seq[T]] = seq match
            case Success(value) +: rest => inv(rest, value +: agg)
            case Failure(exception) +: _ => Failure(exception)
            case _ => Success(agg)

        inv(seq, Seq.empty)

    extension[T] (iterable: Iterable[Try[T]])
        def invertTry: Try[Seq[T]] = TryUtils.invertTry(iterable.toSeq)
