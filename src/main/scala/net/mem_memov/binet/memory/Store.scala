package net.mem_memov.binet.memory

class Store(
  private val blocks: Array[Block]
):

  def write(destination: UnsignedByte, content: Address): Boolean =

    if content.hasLength(blocks.length) then
      content.foreach { part =>
        blocks.foreach { block =>
          block.write(destination, part)
        }
      }
      true
    else
      false

  def read(origin: UnsignedByte): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    new Address(parts)
