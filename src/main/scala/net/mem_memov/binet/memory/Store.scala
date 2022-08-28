package net.mem_memov.binet.memory

import zio.*

/**
 * Store is capable of storing any address of its level and the levels above.
 * All stores have the same height, but a different width which depends on the level.
 * The deeper the level the wider are the stores.
 */
class Store(
  private val blocks: Vector[Block]
):

  def write(destination: UnsignedByte, content: Address): Task[Store] =

    for {
      _ <- if !content.hasLength(blocks.length) then ZIO.fail(Exception("Destination not written: content to large")) else ZIO.unit
      updatedBlocks <- ZIO.collectAll {
          content.indices.zip(blocks).map { case (part, block) =>
              val blockUpdate: Task[Block] = block.write(destination, part)
              blockUpdate
          }
        }
    } yield Store(updatedBlocks.toVector)


  def read(origin: UnsignedByte): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    Address(parts)
