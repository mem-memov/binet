package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

import scala.collection.immutable.Queue

case class TreeStock(
  elements: Vector[Element]
)

object TreeStock:

  given Stock[TreeStock] with

    override
    def writeStock(
      stock: TreeStock,
      index: UnsignedByte,
      destination: Path,
      content: Content
    ): Either[String, TreeStock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)

    override
    def readStock(
      stock: TreeStock,
      index: UnsignedByte,
      origin: Path
    ): Either[String, Content] =

      stock.elements(index.toInt).read(origin)
