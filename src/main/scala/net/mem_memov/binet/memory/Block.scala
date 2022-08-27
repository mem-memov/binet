package net.mem_memov.binet.memory

import scala.collection.mutable

class Block(
  private var current: UnsignedByte,
  private val space: mutable.ArraySeq[UnsignedByte]
):

  def read(position: UnsignedByte): Option[UnsignedByte] = 

    if position > current then
      None
    else
      Some(space(position.toIndex))

  def append(content: UnsignedByte): Option[UnsignedByte] = 

    if current.atMaximum then
      None
    else
      current = current.increment
      space(current.toIndex) = content
      Some(current)

object Block:

  def apply: Block =
    new Block(
      UnsignedByte.minimum,
      mutable.ArraySeq.make[UnsignedByte](
        new Array[UnsignedByte](
          UnsignedByte.maximum.toIndex
        )
      )
    )