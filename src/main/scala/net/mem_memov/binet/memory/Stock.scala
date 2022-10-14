package net.mem_memov.binet.memory

/**
 * Stock connects an element with other elements on a lower level building a tree-like structure.
 */
trait Stock:

  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock]

  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address]

object Stock:

  def apply(elements: Vector[Element]): Stock = new Stock:

    override
    def write(
      index: UnsignedByte,
      destination: Address,
      content: Address
    ): Either[String, Stock] =
      for {
        updatedElement <- elements(index.toInt).write(destination, content)
        updatedElements <- Right(elements.updated(index.toInt, updatedElement))
      } yield Stock(updatedElements)

    override
    def read(
      index: UnsignedByte,
      origin: Address
    ): Either[String, Address] =
      elements(index.toInt).read(origin)
