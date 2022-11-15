package net.mem_memov.binet.memory.specific.address.general.formatter

import net.mem_memov.binet.memory.general.UnsignedByte

trait TrimBig[FORMATTER]:

  def trimBigIndices(
    formatter: FORMATTER,
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  extension (formatter: FORMATTER) {
    def trimBig(
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      trimBigIndices(formatter, indices)
  }




