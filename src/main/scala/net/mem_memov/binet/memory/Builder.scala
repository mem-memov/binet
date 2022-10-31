package net.mem_memov.binet.memory

trait Builder:
  
  def addSlice(
    slice: Array[Byte]
  ): Builder
