package net.mem_memov.binet.memory

import zio.stm._

/**
 * Store is capable of storing any address of its level and the levels above.
 * All stores have the same height, but a different width which depends on the level.
 * The deeper the level the wider are the stores.
 */
class Store(
  private val blocks: TArray[Block]
):

  def write(destination: UnsignedByte, content: Address): STM[String, Unit] =

    if !content.hasLength(blocks.size) then
      STM.fail("Destination not written: content to large")
    else
      val updatedBlocks2 = blocks.zip
      val updatedBlocks = content.indices.zip(blocks).map { case (part, block) =>
        block.update(destination, part)
      }
      Right(Store(updatedBlocks.toVector))


  def read(origin: UnsignedByte): USTM[Address] =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    Address(parts)

