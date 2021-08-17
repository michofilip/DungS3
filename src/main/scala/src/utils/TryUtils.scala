package src.utils

import scala.util.{Failure, Success, Try}

object TryUtils:

    private def _invertTry[T](seq: Seq[Try[T]]): Try[Seq[T]] =
        def inv(seq: Seq[Try[T]], agg: Seq[T]): Try[Seq[T]] = seq match
            case Success(value) +: rest => inv(rest, value +: agg)
            case Failure(exception) +: _ => Failure(exception)
            case _ => Success(agg)

        inv(seq, Seq.empty)

    private def _invertTry[T](opt: Option[Try[T]]): Try[Option[T]] = opt match
        case Some(Success(value)) => Success(Some(value))
        case Some(Failure(exception)) => Failure(exception)
        case None => Success(None)

    extension[T] (iterable: Iterable[Try[T]])
        def invertTry: Try[Seq[T]] = _invertTry(iterable.toSeq)

    extension[T] (option: Option[Try[T]])
        def invertTry: Try[Option[T]] = _invertTry(option)
