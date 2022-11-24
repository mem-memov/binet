package net.mem_memov.binet.memory.specific.address.general.padder

import net.mem_memov.binet.memory.general.UnsignedByte

trait PadBig[PADDER]:

  def f(
    padder: PADDER,
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]

  extension (padder: PADDER)

    def padBig(
      target: Int,
      indices: List[UnsignedByte]
    ): Either[String, List[UnsignedByte]] =

      f(padder, target, indices)
