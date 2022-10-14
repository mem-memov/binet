package net.mem_memov.binet.memory

/**
 * Store is capable of storing any address of its level and the levels above.
 * All stores have the same height, but a different width which depends on the level.
 * The deeper the level the wider are the stores in order to accommodate larger addresses.
 */
trait Store:

  def write(
    destination: UnsignedByte,
    content: Address
  ): Either[String, Store]

  def read(
    origin: UnsignedByte
  ): Address

object Store:

  def apply(blocks: Vector[Block]): Store = new Store:

    def write(
      destination: UnsignedByte,
      content: Address
    ): Either[String, Store] =

      if !content.hasLength(blocks.length) then
        Left("Destination not written: content has wrong number of indices")
      else
        val updatedBlocks = content.zipIndices(blocks).map { case (part, block) =>
          block.write(destination, part)
        }
        Right(Store(updatedBlocks.toVector))

    def read(
      origin: UnsignedByte
    ): Address =

      val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      Address(parts.reverse)