package src.game.entity

import src.data.model.{EntityEntry, PhysicsEntry}
import src.data.repository.EntityPrototypeRepository
import src.game.entity.parts.{Direction, Position, State}
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.xml.{PrettyPrinter, XML}

class EntityService(using entityPrototypeRepository: EntityPrototypeRepository):

    def createEntity(id: UUID,
                     name: String,
                     timestamp: Timestamp,
                     state: Option[State] = None,
                     position: Option[Position] = None,
                     direction: Option[Direction] = None): Option[Entity] =

        entityPrototypeRepository.findById(name).map { entityPrototype =>

            val validState = entityPrototype.getValidatedState(state)
            val validPosition = entityPrototype.getValidatedPosition(position)
            val validDirection = entityPrototype.getValidatedDirection(direction)
            val physicsSelector = entityPrototype.physicsSelector
            val animationSelector = entityPrototype.animationSelector

            Entity(
                id = id,
                name = name,
                timestamp = timestamp,
                state = validState,
                position = validPosition,
                direction = validDirection,
                physicsSelector = physicsSelector,
                animationSelector = animationSelector
            )
        }

    def loadEntitiesFromFile(file: File): Seq[Entity] =
        def convertToEntity(entityEntry: EntityEntry): Option[Entity] =
            val position = for {
                x <- entityEntry.x
                y <- entityEntry.y
            } yield {
                Position(x, y)
            }

            createEntity(
                id = UUID.fromString(entityEntry.id),
                name = entityEntry.name,
                timestamp = Timestamp(entityEntry.timestamp),
                state = entityEntry.state,
                position = position,
                direction = entityEntry.direction
            )

        val xml = XML.loadFile(file)
        (xml \ "Entity")
            .flatMap(EntityEntry.fromXML)
            .flatMap(convertToEntity)

    def saveEntitiesToFile(file: File, entities: Seq[Entity]): Unit =
        def convertToEntry(entity: Entity): EntityEntry =
            EntityEntry(
                id = entity.id.toString,
                name = entity.name,
                timestamp = entity.timestamp.milliseconds,
                state = entity.state,
                x = entity.position.map(_.x),
                y = entity.position.map(_.y),
                direction = entity.direction
            )

        val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
        val prettyPrinter = new PrettyPrinter(80, 2)

        val xml =
            <entities>
                {entities.map(convertToEntry).map(_.xml)}
            </entities>

        printWriter.println(prettyPrinter.format(xml))
        printWriter.close()
