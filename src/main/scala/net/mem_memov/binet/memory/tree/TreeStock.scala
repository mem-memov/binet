package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

import scala.collection.immutable.Queue

case class TreeStock(
  elements: Vector[Element]
)

object TreeStock:

  given Stock[TreeStock, TreeContent, TreePath] with

    override
    def writeStock(
      stock: TreeStock,
      index: UnsignedByte,
      destination: TreePath,
      content: TreeContent
    ): Either[String, TreeStock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)

    override
    def readStock(
      stock: TreeStock,
      index: UnsignedByte,
      origin: TreePath
    ): Either[String, TreeContent] =

      stock.elements(index.toInt).read(origin)
