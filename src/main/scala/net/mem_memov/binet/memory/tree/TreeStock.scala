package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

import scala.collection.immutable.Queue

case class TreeStock(
  elements: Vector[Element]
) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
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
  def enqueueElements(
    queue: Queue[Element]
  ): Queue[Element] =

    queue.enqueueAll(elements)