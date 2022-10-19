package net.mem_memov.binet.memory.stock

import net.mem_memov.binet.memory.{Address, Element, Stock, UnsignedByte}

class DefaultStock(elements: Vector[Element]) extends Stock:

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
