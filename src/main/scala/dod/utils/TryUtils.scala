package dod.utils

import scala.util.{Failure, Success, Try}

object TryUtils:

    private def _invertTry[T](seq: Seq[Try[T]]): Try[Seq[T]] =
        def inv(seq: Seq[Try[T]], agg: Seq[T]): Try[Seq[T]] = seq match
            case Success(value) +: rest => inv(rest, value +: agg)
            case Failure(exception) +: _ => Failure(exception)
            case _ => Success(agg.reverse)

        inv(seq, Seq.empty)

    private def _invertTry[T](opt: Option[Try[T]]): Try[Option[T]] = opt match
        case Some(Success(value)) => Success(Some(value))
        case Some(Failure(exception)) => Failure(exception)
        case None => Success(None)

    private def _toTry[T](opt: Option[T], throwable: Throwable): Try[T] = opt match
        case Some(value) => Success(value)
        case None => Failure(throwable)


    extension[T] (seq: Seq[Try[T]])
        def toTrySeq: Try[Seq[T]] = _invertTry(seq)

    extension[T] (option: Option[Try[T]])
        def toTryOption: Try[Option[T]] = _invertTry(option)

    extension[T] (option: Option[T])
        def toTry(throwable: Throwable): Try[T] = _toTry(option, throwable)