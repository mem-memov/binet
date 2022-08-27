package net.mem_memov.binet.memory

private[memory] class Stock(
  private val elements: Array[Element]
):

  def write(index: UnsignedByte, destination: Address, content: Address): Boolean =
    elements(index.toInt).write(destination, content)

  def read(index: UnsignedByte, origin: Address): Option[Address] =
    elements(index.toInt).read(origin)