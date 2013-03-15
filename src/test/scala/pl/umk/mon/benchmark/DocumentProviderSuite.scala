package pl.umk.mon.benchmark
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.JavaConversions._

@RunWith(classOf[JUnitRunner])
class DocumentProviderSuite extends FunSuite {
  def prepareContainerMap(): ContainerMap = {
    val nouns = List("Bob", "Alice")
    val verbs = List("hurt", "maim", "kill")
    val adjs = List("strange", "horrible", "obese", "ubiqous")
    val columns = Map("upvote" -> Right(NumericColumnType(0, 3)), "creationDate" -> Right(DateColumnType()), "content" -> Right(SentenceColumnType(verbs, nouns, adjs)))
    val cm = new ContainerMap
    cm.putAll(columns)
    cm
  }

  test("check list size of documents to insert") {
    val n = 10
    val cm = prepareContainerMap()
    val dp = new DocumentProvider(cm, n)
    assert(dp.documentsToInsert().size === n)
  }
}
