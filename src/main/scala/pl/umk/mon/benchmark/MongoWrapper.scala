package pl.umk.mon.benchmark
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import com.mongodb.casbah.query.Imports.{ DBObject => QueryDBObject }
import scala.collection.JavaConversions._
import com.mongodb.casbah.query.dsl._

class MongoWrapper(val dbName: String, collectionName: String) extends Wrapper {
  val mongoClient = MongoClient()
  RegisterJodaTimeConversionHelpers()

  private def mongify(map: JsonMap): DBObject = {
    val builder = MongoDBObject.newBuilder
    map.foldLeft(builder)((b, kv) => {
      val key = kv._1
      kv._2 match {
        case Left(map) => {
          b += key -> mongify(map).result
        }
        case Right(value) => {
          b += key -> value
        }
      }
      b
    })
    builder.result
  }

  def insert(what: List[JsonMap]) = {
    val collection = mongoClient(dbName)(collectionName)
    
    val problem = what.map(x => mongify(x: JsonMap))
    problem.foldLeft(collection)({(c,x) => c+=x;c})
    
  }

  private def translate(x: JsonCriteria): MongoDBObject = {
    x match {
//      case NumericCriteria(n, m, l) => { (n $lt 50 $gt 5) }
      case EqualCriteria(n, w) => { MongoDBObject(n -> w) }
    }
  }

  def search(criteria: List[JsonCriteria]) = {
    val collection = mongoClient(dbName)(collectionName)
    val q = criteria.map(translate).reduce {
      (a, b) =>
        a.++[MongoDBObject](b)
        a
    }
    val cursor = collection.find(q)
    for (x <- cursor) {
      println(x)
    }
  }

  def remove(criteria: List[JsonCriteria]) = {

  }
}