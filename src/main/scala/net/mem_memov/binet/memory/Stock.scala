package net.mem_memov.binet.memory

private[memory] class Stock(
  private val elements: Vector[Element]
):

  def write(index: UnsignedByte, destination: Address, content: Address): Either[String, Stock] =
    for {
      updatedElement <- elements(index.toInt).write(destination, content)
      updatedElements <- Right(elements.updated(index.toInt, updatedElement))
    } yield Stock(updatedElements)

  def read(index: UnsignedByte, origin: Address): Either[String, Address] =
    elements(index.toInt).read(origin)