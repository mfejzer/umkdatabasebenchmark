package pl.umk.mon.benchmark
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConfTestSuite extends FunSuite {
  test("conf with all parameters") {
    val args = List("--couchDbName", "someCouchDbName",
      "--mongoCollectionName", "someMongoCollectionName",
      "--mongoDbName", "someMongoDbName",
      "--numberOfInserts", "5000")
    val conf = new Conf(args)
    assert(conf.mongoDbName.isDefined === true)
    assert(conf.mongoCollectionName.isDefined === true)
    assert(conf.couchDbName.isDefined === true)
    assert(conf.inserts.isDefined === true)
    assert(conf.mongoDbName.get.get ==="someMongoDbName")
    assert(conf.mongoCollectionName.get.get === "someMongoCollectionName")
    assert(conf.couchDbName.get.get ===  "someCouchDbName")
    assert(conf.inserts.get.get === 5000)
  }
}