package net.mem_memov.binet.memory.stock

import net.mem_memov.binet.memory._

case class DefaultStock(
  elements: Vector[Element]
) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock] =

    DefaultStock.write(index, destination, content, elements, updateWithElements)

  override
  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address] =

    DefaultStock.read(index, origin, elements)

  def updateWithElements(
    updatedElements: Vector[Element]
  ): Stock =

    this.copy(elements = updatedElements)

object DefaultStock:

  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address,
    elements: Vector[Element],
    updateWithElements: Vector[Element] => Stock,
  ): Either[String, Stock] =

    for {
      updatedElement <- elements(index.toInt).write(destination, content)
      updatedElements <- Right(elements.updated(index.toInt, updatedElement))
    } yield updateWithElements(updatedElements)

  def read(
    index: UnsignedByte,
    origin: Address,
    elements: Vector[Element]
  ): Either[String, Address] =

    elements(index.toInt).read(origin)