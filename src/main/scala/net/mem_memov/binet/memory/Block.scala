package net.mem_memov.binet.memory

import zio.stm._
import scala.collection.mutable

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
class Block(
  private val space: TArray[UnsignedByte]
):

  def read(position: UnsignedByte): USTM[UnsignedByte] =
    space(position.toInt)

  def write(position: UnsignedByte, content: UnsignedByte): USTM[Unit] =
    space.update(position.toInt, _ => content)

object Block:

  def apply(): USTM[Block] =
    TArray.fromIterable(mutable.ArraySeq.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)).map { space =>
      new Block(space)
    }

  def apply(space: TArray[UnsignedByte]): USTM[Block] =
    STM.succeed(new Block(space))
