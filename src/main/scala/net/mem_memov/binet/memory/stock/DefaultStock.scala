package net.mem_memov.binet.memory.stock

import net.mem_memov.binet.memory.{Address, Depth, Element, Stock, UnsignedByte}

case class DefaultStock(elements: Vector[Element]) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock.Write] =
    for {
      elementWrite <- elements(index.toInt).write(destination, content)
      updatedElements <- Right(elements.updated(index.toInt, elementWrite.element))
    } yield
      val updatedStock = this.copy(elements = updatedElements)
      Stock.Write(updatedStock, elementWrite.depth)

  override
  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address] =
    elements(index.toInt).read(origin)

