package net.mem_memov.binet.memory

import zio.*

private[memory] class Stock(
  private val elements: Vector[Element]
):

  def write(index: UnsignedByte, destination: Address, content: Address): Task[Stock] =
    for {
      updatedElement <- elements(index.toInt).write(destination, content)
      updatedElements <- ZIO.succeed(elements.updated(index.toInt, updatedElement))
    } yield Stock(updatedElements)

  def read(index: UnsignedByte, origin: Address): Task[Address] =
    elements(index.toInt).read(origin)