package net.mem_memov.binet.memory

trait UnusedStock(fail: String => Nothing) extends Stock:

  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock] =
    
    fail("unexpected")

  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address] =
    
    fail("unexpected")
