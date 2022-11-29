package net.mem_memov.binet.memory.general.address

trait ToLittleEndian[ADDRESS]:
  
  def f(address: ADDRESS): Array[Byte]
  
  extension (address: ADDRESS)
    
    def toLittleEndian: Array[Byte] =

      f(address)
