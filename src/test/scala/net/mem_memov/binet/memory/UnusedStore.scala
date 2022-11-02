package net.mem_memov.binet.memory

trait UnusedStore(fail: String => Nothing) extends Store:

  def write(
    destination: UnsignedByte,
    content: Content
  ): Store =

    fail("unexpected")

  override 
  def read(
    origin: UnsignedByte
  ): Address =

    fail("unexpected")

  override 
  def expand(
    minimumLength: Int
  ): Store =

    fail("unexpected")

    