package net.mem_memov.binet.memory

trait UnusedBlock(fail: String => Nothing) extends Block:

  override
  def isEmpty: Boolean =

    fail("unexpected")

  override 
  def read(
    position: UnsignedByte
  ): UnsignedByte = 
    
    fail("unexpected")
    
  override 
  def write(
    position: UnsignedByte, 
    content: UnsignedByte
  ): Block = 
    
    fail("unexpected")