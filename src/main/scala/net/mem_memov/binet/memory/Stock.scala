package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.TreeStock

import scala.collection.immutable.Queue

/**
 * Stock connects an element with other elements on a lower level building a tree-like structure.
 */
trait Stock[S]:

  def writeStock(
    stock: S,
    index: UnsignedByte,
    destination: Path,
    content: Content
  ): Either[String, S]

  def readStock(
    stock: S,
    index: UnsignedByte,
    origin: Path
  ): Either[String, Content]

  extension (stock: S)

    def write(
      index: UnsignedByte,
      destination: Path,
      content: Content
    ): Either[String, S] =

      writeStock(stock, index, destination, content)

    def read(
      index: UnsignedByte,
      origin: Path
    ): Either[String, Content] =

      readStock(stock, index, origin)




