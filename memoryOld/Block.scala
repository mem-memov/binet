package net.mem_memov.binet.memoryOld

import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.TreeBlock

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
trait Block[B]:

  def isBlockEmpty(
    block: B
  ): Boolean

  def writeBlock(
    block: B,
    position: UnsignedByte,
    content: UnsignedByte
  ): B

  def readBlock(
    block: B,
    position: UnsignedByte
  ): UnsignedByte

  extension (block: B)

    def isEmpty: Boolean =

      isBlockEmpty(block)

    def write(
      position: UnsignedByte,
      content: UnsignedByte
    ): B =

      writeBlock(block, position, content)

    def read(
      position: UnsignedByte
    ): UnsignedByte =

      readBlock(block, position)