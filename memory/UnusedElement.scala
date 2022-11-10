package net.mem_memov.binet.memory

trait UnusedElement(fail: String => Nothing) extends Element:

  override
  def write(
    destination: Path,
    content: Content
  ): Either[String, Element] =

    fail("unexpected")

  override
  def read(
    origin: Path
  ): Either[String, Content] =

    fail("unexpected")