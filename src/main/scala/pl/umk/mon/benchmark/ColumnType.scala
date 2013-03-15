package pl.umk.mon.benchmark

abstract class ColumnType
case class NumericColumnType(val min: Integer,
  val max: Integer) extends ColumnType
case class DateColumnType extends ColumnType
case class SentenceColumnType(val verbs: List[String],
  val nouns: List[String], val adjectives: List[String]) extends ColumnType

//type Banana = Map[String,Either[ColumnType,Banana]]
//Doesn't work, illegal cyclic refercence ?

//Workaround
class ContainerMap extends java.util.HashMap[String, Either[ContainerMap,ColumnType]]
class JsonMap extends java.util.HashMap[String, Either[JsonMap,String]]