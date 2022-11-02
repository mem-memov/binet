package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

import scala.collection.immutable.Queue

case class TreeStock(
  elements: Vector[Element]
) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Path,
    content: Content
  ): Either[String, TreeStock] =

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

  override
  def read(
    index: UnsignedByte,
    origin: Path
  ): Either[String, Content] =

    elements(index.toInt).read(origin)
