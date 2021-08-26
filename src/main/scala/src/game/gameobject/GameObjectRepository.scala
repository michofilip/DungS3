package src.game.gameobject

import src.game.gameobject.GameObject
import src.game.gameobject.parts.position.Position

import java.util.UUID

class GameObjectRepository private(private val gameObjectsById: Map[UUID, GameObject],
                                   private val gameObjectsByPosition: Map[Position, Map[UUID, GameObject]]):

    def +(gameObject: GameObject): GameObjectRepository =
        val newGameObjectByPosition = gameObject.position.fold(gameObjectsByPosition) { position =>
            val newGameObjectAtPosition = gameObjectsByPosition.getOrElse(position, Map.empty) + (gameObject.id -> gameObject)

            gameObjectsByPosition + (position -> newGameObjectAtPosition)
        }

        val newGameObjectsById = gameObjectsById + (gameObject.id -> gameObject)

        new GameObjectRepository(newGameObjectsById, newGameObjectByPosition)

    def ++(gameObjects: Seq[GameObject]): GameObjectRepository =
        gameObjects.foldLeft(this)(_ + _)

    def -(gameObject: GameObject): GameObjectRepository =
        val newGameObjectByPosition = gameObject.position.fold(gameObjectsByPosition) { position =>
            val newGameObjectAtPosition = gameObjectsByPosition.getOrElse(position, Map.empty) - gameObject.id

            if (newGameObjectAtPosition.isEmpty)
                gameObjectsByPosition - position
            else
                gameObjectsByPosition + (position -> newGameObjectAtPosition)
        }

        val newGameObjectsById = gameObjectsById - gameObject.id

        new GameObjectRepository(newGameObjectsById, newGameObjectByPosition)

    def --(gameObjects: Seq[GameObject]): GameObjectRepository =
        gameObjects.foldLeft(this)(_ - _)

    def findAll: Seq[GameObject] =
        gameObjectsById.values.toSeq

    def findById(id: UUID): Option[GameObject] =
        gameObjectsById.get(id)

    def findByPosition(position: Position): Map[UUID, GameObject] =
        gameObjectsByPosition.getOrElse(position, Map.empty)

    def existAtPosition(position: Position)(predicate: GameObject => Boolean): Boolean =
        findByPosition(position).values.exists(predicate)

    def forallAtPosition(position: Position)(predicate: GameObject => Boolean): Boolean =
        findByPosition(position).values.forall(predicate)

    def existSolidAtPosition(position: Position): Boolean = existAtPosition(position: Position) { gameObject =>
        gameObject.physics.fold(false)(_.solid)
    }

    override def toString: String = findAll.mkString("[", ", ", "]")


object GameObjectRepository:
    def apply(gameObjects: Seq[GameObject] = Seq.empty): GameObjectRepository =
        val gameObjectRepository = new GameObjectRepository(Map.empty, Map.empty)

        if gameObjects.nonEmpty then
            gameObjectRepository ++ gameObjects
        else
            gameObjectRepository
