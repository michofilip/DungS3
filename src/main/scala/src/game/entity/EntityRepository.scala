package src.game.entity

import src.game.entity.Entity
import src.game.entity.parts.Position

import java.util.UUID

class EntityRepository private(private val entitiesById: Map[UUID, Entity],
                               private val entitiesByPosition: Map[Position, Map[UUID, Entity]]):

    def +(entity: Entity): EntityRepository =
        val newEntitiesByPosition = entity.position.fold(entitiesByPosition) { position =>
            val newEntitiesAtPosition = entitiesByPosition.getOrElse(position, Map.empty) + (entity.id -> entity)

            entitiesByPosition + (position -> newEntitiesAtPosition)
        }

        val newEntitiesById = entitiesById + (entity.id -> entity)

        new EntityRepository(newEntitiesById, newEntitiesByPosition)

    def ++(entities: Seq[Entity]): EntityRepository =
        entities.foldLeft(this)(_ + _)

    def -(entity: Entity): EntityRepository =
        val newEntitiesByPosition = entity.position.fold(entitiesByPosition) { position =>
            val newEntitiesAtPosition = entitiesByPosition.getOrElse(position, Map.empty) - entity.id

            if (newEntitiesAtPosition.isEmpty)
                entitiesByPosition - position
            else
                entitiesByPosition + (position -> newEntitiesAtPosition)
        }

        val newIdToEntity = entitiesById - entity.id

        new EntityRepository(newIdToEntity, newEntitiesByPosition)

    def --(entities: Seq[Entity]): EntityRepository =
        entities.foldLeft(this)(_ - _)

    def findById(id: UUID): Option[Entity] = entitiesById.get(id)

    def findByPosition(position: Position): Map[UUID, Entity] = entitiesByPosition.getOrElse(position, Map.empty)

    def existAtPosition(position: Position)(predicate: Entity => Boolean): Boolean = findByPosition(position).values.exists(predicate)

    def forallAtPosition(position: Position)(predicate: Entity => Boolean): Boolean = findByPosition(position).values.forall(predicate)

    def existSolidAtPosition(position: Position): Boolean = existAtPosition(position: Position) { entity =>
        entity.physics.fold(false)(_.solid)
    }

    override def toString: String = entitiesById.values.mkString("Entities(", ", ", ")")


object EntityRepository:
    def apply(entities: Seq[Entity] = Seq.empty): EntityRepository =
        val entityRepository = new EntityRepository(Map.empty, Map.empty)

        if entities.nonEmpty then
            entityRepository ++ entities
        else
            entityRepository
