package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

case class TreeStock(
  elements: Vector[Element]
) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, (TreeStock, Option[Element])] =

    for {
      writeResult <- elements(index.toInt).write(destination, content)
      (updatedElement, initiatedElementOption) = writeResult
      updatedElements <- Right(elements.updated(index.toInt, updatedElement))
    } yield
      val modifiedStock = this.copy(elements = updatedElements)
      (modifiedStock, initiatedElementOption)

  override
  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address] =

    elements(index.toInt).read(origin)
