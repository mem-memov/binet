package net.mem_memov.binet.memoryOld

import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.TreeStock

import scala.collection.immutable.Queue

/**
 * Stock connects an element with other elements on a lower level building a tree-like structure.
 */
trait Stock[S, C : Content, P : Path]:

  def writeStock(
    stock: S,
    index: UnsignedByte,
    destination: P,
    content: C
  ): Either[String, S]

  def readStock(
    stock: S,
    index: UnsignedByte,
    origin: P
  ): Either[String, Content]

  extension (stock: S)

    def write(
      index: UnsignedByte,
      destination: P,
      content: C
    ): Either[String, S] =

      writeStock(stock, index, destination, content)

    def read(
      index: UnsignedByte,
      origin: P
    ): Either[String, C] =

      readStock(stock, index, origin)




