package net.mem_memov.binet.memory.specific.specificAddress.general.formatter

import net.mem_memov.binet.memory.general.UnsignedByte

trait TrimmingFormatter[FORMATTER]:

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



