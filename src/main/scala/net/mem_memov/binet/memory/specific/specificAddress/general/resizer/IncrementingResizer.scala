package net.mem_memov.binet.memory.specific.specificAddress.general.resizer

import net.mem_memov.binet.memory.general.UnsignedByte

trait IncrementingResizer[RESIZER]:

  def incrementIndices(
    resizer: RESIZER,
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  extension (resizer: RESIZER)

    def increment(
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      incrementIndices(resizer, indices)


