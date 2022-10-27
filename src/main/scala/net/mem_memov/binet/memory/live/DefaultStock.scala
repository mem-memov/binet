package net.mem_memov.binet.memory.live

import net.mem_memov.binet.memory.*

case class DefaultStock(
  elements: Vector[Element]
) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, DefaultStock] =

    for {
      updatedElement <- elements(index.toInt).write(destination, content)
      updatedElements <- Right(elements.updated(index.toInt, updatedElement))
    } yield this.copy(elements = updatedElements)

  override
  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address] =

    elements(index.toInt).read(origin)
