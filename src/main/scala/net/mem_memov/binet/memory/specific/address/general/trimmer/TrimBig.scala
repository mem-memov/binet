package net.mem_memov.binet.memory.specific.address.general.trimmer

import net.mem_memov.binet.memory.general.UnsignedByte

trait TrimBig[TRIMMER]:

  private[TrimBig]
  def f(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  extension (trimmer: TRIMMER) 
    
    def trimBig(
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      f(indices)
  
