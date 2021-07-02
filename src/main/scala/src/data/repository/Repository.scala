package src.data.repository

trait Repository[ID, T]:
    protected val dataById: Map[ID, T]

    final def findById(id: ID): Option[T] = dataById.get(id)
    