package net.mem_memov.binet.memory

import scala.collection.mutable

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 */
class Block(
  private val space: mutable.ArraySeq[UnsignedByte]
):

  def read(position: UnsignedByte): UnsignedByte =
    space(position.toInt)

  def write(position: UnsignedByte, content: UnsignedByte): Unit =
    space(position.toInt) = content

object Block:

  def apply(): Block =
    new Block(
      mutable.ArraySeq.make[UnsignedByte](
        new Array[UnsignedByte](
          UnsignedByte.maximum.toInt + 1
        )
      )
    )