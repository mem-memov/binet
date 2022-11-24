package net.mem_memov.binet.memory.specific.address.general.trimmer

import net.mem_memov.binet.memory.general.UnsignedByte

trait TrimBig[TRIMMER]:

  def f(
    trimmer: TRIMMER,
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  extension (trimmer: TRIMMER) {
    def trimBig(
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      f(trimmer, indices)
  }
