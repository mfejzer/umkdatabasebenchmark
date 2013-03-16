package pl.umk.mon.benchmark
import scala.collection.mutable.ListBuffer
import scala.util.Random
import java.util.Date
import scala.collection.immutable.TreeMap
import scala.collection.JavaConversions._

class DocumentProvider(val columns: ContainerMap, val numberOfDocuments: Integer) {
  private val random = new Random()
  private val documents: ListBuffer[JsonMap] = new ListBuffer()
  for (i <- 1 to numberOfDocuments) {
    documents.append(generateDocument(columns))
  }

  def documentsToBeInserted(): List[JsonMap] = { documents.toList }

  def generateDocument(columns: ContainerMap): JsonMap = {
    columns.foldLeft(new JsonMap)(handleColumnTypes)
  }

  def handleColumnTypes(jsonMap: JsonMap, pair: (String, Either[ContainerMap, ColumnType])): JsonMap = {
    val key = pair._1
    val either = pair._2
    either match {
      case Right(columnType) => {
        columnType match {
          case NumericColumnType(min, max) => {
            val r = random.nextInt(max - min) + min
            val e = Right(r.toString)
            jsonMap.put(key, e)
            jsonMap
          }
          case DateColumnType() => {
            val e = Right((new Date()).toString)
            jsonMap.put(key, e)
            jsonMap
          }
          case SentenceColumnType(verbs, nouns, adjectives) => {
            def getRandom(from: List[String]): String = from(random.nextInt(from.length))
            val e = Right(getRandom(nouns) + " " +
              getRandom(verbs) + " " +
              getRandom(adjectives) + " " +
              getRandom(nouns) + ".")
            jsonMap.put(key, e)
            jsonMap

          }
        }
      }
      case Left(containerMap) => {
        val tmp = containerMap.foldLeft(new JsonMap)(handleColumnTypes)
        val e = Left(tmp)
        jsonMap.put(key, e)
        jsonMap
      }
    }
  }
}

