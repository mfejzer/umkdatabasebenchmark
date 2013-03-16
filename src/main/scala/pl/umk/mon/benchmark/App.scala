package pl.umk.mon.benchmark
import scala.collection.JavaConversions._

object App {

  def main(args: Array[String]) {
    val nouns = List("Bob", "Alice")
    val verbs = List("hurt", "maim", "kill")
    val adjs = List("strange", "horrible", "obese", "ubiqous")

    val columns = Map("upvote" -> Right(NumericColumnType(0, 3)), "creationDate" -> Right(DateColumnType()), "content" -> Right(SentenceColumnType(verbs, nouns, adjs)))
    val cm = new ContainerMap
    cm.putAll(columns)

    val ecolumns = Map("comment" -> Right(SentenceColumnType(verbs, nouns, adjs)), "rating" -> Right(NumericColumnType(0, 6)))
    val ecm = new ContainerMap
    ecm.putAll(ecolumns)
    cm.put("embedded", Left(ecm))

    val dp = new DocumentProvider(cm, 10000)

    val mv = new MongoWrapper("test","test")
    val cv = new CouchWrapper("test")
    
    println("Time of insertion for mongo: ",mv.measureTimeOfInsertion(dp))
    println("Time of insertion for couch: ",cv.measureTimeOfInsertion(dp))
  }

}


