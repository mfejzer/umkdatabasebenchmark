package pl.umk.mon.benchmark

trait Wrapper {
  def measureTimeOfInsertion(documentProvider: DocumentProvider): Long = {
    val before = System.nanoTime
    insert(documentProvider.documentsToBeInserted())
    val after = System.nanoTime
    after-before
  }
  
  def measureTimeOfSearch(criteria: List[JsonCriteria]): Long = {
    val before = System.nanoTime
    search(criteria)
    val after = System.nanoTime
    after-before
  }
  
  def measureTimeOfRemoval(criteria: List[JsonCriteria]): Long = {
    val before = System.nanoTime
    remove(criteria)
    val after = System.nanoTime
    after-before
  }
  
  def insert(what: List[JsonMap])
  def search(criteria: List[JsonCriteria])
  def remove(criteria: List[JsonCriteria])
}