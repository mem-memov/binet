package net.mem_memov.binet.memory.specific.address.general.resizer

import net.mem_memov.binet.memory.general.UnsignedByte

trait Increment[RESIZER]:

  def f(
    resizer: RESIZER,
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  extension (resizer: RESIZER)

    def increment(
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      f(resizer, indices)


