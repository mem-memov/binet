package net.mem_memov.binet.memory

/**
 * Store is capable of storing any address of its level and the levels above.
 * All stores have the same height, but a different width which depends on the level.
 * The deeper the level the wider are the stores.
 */
class Store(
  private val blocks: Vector[Block]
):

  def write(destination: UnsignedByte, content: Address): Either[Throwable, Store] =

    if !content.hasLength(blocks.length) then 
      Left(Exception("Destination not written: content to large"))
    else
      val updatedBlocks = content.indices.zip(blocks).map { case (part, block) => 
        block.write(destination, part)
      }
      Right(Store(updatedBlocks.toVector))


  def read(origin: UnsignedByte): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    Address(parts)
