package net.mem_memov.binet.memory

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 * Data represented by addresses is kept in a store.
 * The number of addresses in the store and the number of references to other elements in the stock are the same.
 * The level determines the number of parts of addresses in the store.
 */
trait Element:

  def write(
    destination: Path,
    content: Content
  ): Either[String, Element]

  def read(
    origin: Path
  ): Either[String, Content]




