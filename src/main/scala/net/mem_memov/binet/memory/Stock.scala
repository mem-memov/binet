package net.mem_memov.binet.memory

import zio.stm._

private[memory] class Stock(
  private val elements: TArray[Element]
):

  def write(index: UnsignedByte, destination: Address, content: Address): STM[String, Unit] =
    for {
      updatedElement <- elements(index.toInt).write(destination, content)
      updatedElements <- Right(elements.updated(index.toInt, updatedElement))
    } yield Stock(updatedElements)

  def read(index: UnsignedByte, origin: Address): Either[Throwable, Address] =
    elements(index.toInt).read(origin)