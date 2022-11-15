package net.mem_memov.binet.memory.specific.address.general.resizer

import net.mem_memov.binet.memory.general.UnsignedByte

trait DecrementingResizer[RESIZER]:

  def decrementIndices(
    resizer: RESIZER,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]

  extension (resizer: RESIZER)

    def decrement(
      indices: List[UnsignedByte]
    ): Either[String, List[UnsignedByte]] =

      decrementIndices(resizer, indices)


