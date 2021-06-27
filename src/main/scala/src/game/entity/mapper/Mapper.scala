package src.game.entity.mapper

trait Mapper[T] extends (Option[T] => Option[T])
