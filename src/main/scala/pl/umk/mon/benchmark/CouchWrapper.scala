package pl.umk.mon.benchmark
import org.ektorp.http.StdHttpClient
import org.ektorp.impl.StdCouchDbInstance
import org.ektorp.http.HttpClient
import org.ektorp.CouchDbInstance
import org.ektorp.CouchDbConnector
import java.util.HashMap
import scala.collection.JavaConversions._
import org.ektorp.ViewQuery
import java.io.InputStream
import org.codehaus.jackson.map.ObjectMapper

class CouchWrapper(val database:String) extends Wrapper {
  val httpClient: HttpClient = new StdHttpClient.Builder().build()
  val dbInstance: CouchDbInstance = new StdCouchDbInstance(httpClient);
  val db: CouchDbConnector = dbInstance.createConnector(database, true);

  private def couchify(map: JsonMap): HashMap[String, Object] = {
    val builder = new HashMap[String, Object]
    map.foldLeft(builder)((b, kv) => {
      val key = kv._1
      kv._2 match {
        case Left(map) => {
          b += key -> couchify(map)
        }
        case Right(value) => {
          b += key -> value
        }
      }
      b
    })
    builder
  }

  def insert(what: List[JsonMap]) = {
    what.map(x => couchify(x: JsonMap)).map(x => db.create(x))
  }
  
  private def translate(x: JsonCriteria): String = {
    x match {
//      case NumericCriteria(n, m, l) => { (?????) }
//      case EqualCriteria(n, w) => { n+":"+w }
      case EqualCriteria(n, w) => { w }
    }
  }
  
  def search(criteria: List[JsonCriteria]) = {
    val docIds = criteria.map(x=>translate(x))
    val query = new ViewQuery()
                      .allDocs()
                      .includeDocs(true)
                      .keys(docIds)
    val is = db.queryForStream(query)
    val mapper = new ObjectMapper()
    val json = mapper.readTree(is)
    println(json)
  }
  
  def remove(criteria: List[JsonCriteria]) = {
    
  }
}