package src.game.entity.mapper

trait Mapper[T] extends (T => T)
