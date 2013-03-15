package pl.umk.mon.benchmark
import scala.collection.JavaConversions._

object App {

  def main(args: Array[String]) {
    val nouns = List("Bob","Alice")
    val verbs = List("hurt","maim","kill")
    val adjs = List("strange","horrible","obese","ubiqous")
    
    val columns = Map("upvote" -> Right(NumericColumnType(0,3)),"creationDate"->Right(DateColumnType()),"content"->Right(SentenceColumnType(verbs,nouns,adjs)))
    val cm = new ContainerMap
    cm.putAll(columns)
    
    val ecm = new ContainerMap
    ecm.putAll(columns)
    cm.put("embedded",Left(ecm))
    
    val dp = new DocumentProvider(cm,10)
    dp.documentsToInsert().map(x=>println(x))
//    println(dp.documentsToInsert().size)
  }
  

}


