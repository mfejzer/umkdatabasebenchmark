package pl.umk.mon.benchmark

trait Wrapper {
  def measureTimeOfInsertion(documentProvider: DocumentProvider): Long
  def measureTimeOfSearch(documentProvider: DocumentProvider): Long
  def measureTimeOfRemoval(documentProvider: DocumentProvider): Long
  def insert()
  def search()
  def remove()
}