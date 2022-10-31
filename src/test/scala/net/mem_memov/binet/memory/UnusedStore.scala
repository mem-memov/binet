package net.mem_memov.binet.memory

trait UnusedStore(fail: String => Nothing) extends Store:

  override 
  def write(
    destination: UnsignedByte, 
    content: Address
  ): Either[String, Store] =

    fail("unexpected")

  def write(
    destination: UnsignedByte,
    content: Vector[UnsignedByte]
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

  override
  def foreachSlice(
    f: Array[Byte] => Unit
  ): Unit =

    fail("unexpected")
    