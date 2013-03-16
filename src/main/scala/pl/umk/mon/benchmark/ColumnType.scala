package pl.umk.mon.benchmark
import scala.collection.JavaConversions._

abstract class ColumnType
case class NumericColumnType(val min: Integer,
  val max: Integer) extends ColumnType
case class DateColumnType extends ColumnType
case class SentenceColumnType(val verbs: List[String],
  val nouns: List[String], val adjectives: List[String]) extends ColumnType

//type Banana = Map[String,Either[ColumnType,Banana]]
//Doesn't work, illegal cyclic refercence ?

//Workaround
class ContainerMap extends java.util.HashMap[String, Either[ContainerMap, ColumnType]]
class JsonMap extends java.util.HashMap[String, Either[JsonMap, String]]

abstract class JsonCriteria
//case class NumericCriteria(val name: String, val moreThan: Integer, val lessThan: Integer) extends JsonCriteria
case class EqualCriteria(val name: String, val content: String) extends JsonCriteria
