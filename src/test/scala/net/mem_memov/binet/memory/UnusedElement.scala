package net.mem_memov.binet.memory

trait UnusedElement(fail: String => Nothing) extends Element:

  def write(
    destination: Address,
    content: Address
  ): Either[String, Element] =
    
    fail("unexpected")

  def read(
    origin: Address
  ): Either[String, Address] =
    
    fail("unexpected")