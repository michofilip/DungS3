package src.entity.mapper

trait Mapper[T] extends (Option[T] => Option[T])
