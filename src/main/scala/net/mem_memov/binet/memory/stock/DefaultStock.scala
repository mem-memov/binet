package net.mem_memov.binet.memory.stock

import net.mem_memov.binet.memory.{Address, Depth, Element, Stock, UnsignedByte}

case class DefaultStock(elements: Vector[Element]) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock] =
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


object DefaultStock:

  lazy val size: Int = UnsignedByte.maximum.toInt + 1