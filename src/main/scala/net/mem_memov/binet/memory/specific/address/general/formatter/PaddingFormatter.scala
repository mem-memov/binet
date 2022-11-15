package net.mem_memov.binet.memory.specific.address.general.formatter

import net.mem_memov.binet.memory.general.UnsignedByte

trait PaddingFormatter[FORMATTER]:

  def padBigIndices(
    formatter: FORMATTER,
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]

  extension (formatter: FORMATTER)

    def padBig(
      target: Int,
      indices: List[UnsignedByte]
    ): Either[String, List[UnsignedByte]] =

      padBigIndices(formatter, target, indices)