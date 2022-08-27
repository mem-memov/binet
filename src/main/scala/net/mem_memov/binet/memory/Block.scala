package net.mem_memov.binet.memory

import scala.collection.mutable

class Block(
  private val space: mutable.ArraySeq[UnsignedByte]
):

  def read(position: UnsignedByte): UnsignedByte =
    space(position.toIndex)

  def write(position: UnsignedByte, content: UnsignedByte): Unit =
    space(position.toIndex) = content

object Block:

  def apply(): Block =
    new Block(
      mutable.ArraySeq.make[UnsignedByte](
        new Array[UnsignedByte](
          UnsignedByte.maximum.toIndex + 1
        )
      )
    )