package net.mem_memov.binet.memory.specific.address.general.resizer

import net.mem_memov.binet.memory.general.UnsignedByte

trait Decrement[RESIZER]:

  private[Decrement]
  def f(
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]

  extension (resizer: RESIZER)

    def decrement(
      indices: List[UnsignedByte]
    ): Either[String, List[UnsignedByte]] =

      f(indices)


