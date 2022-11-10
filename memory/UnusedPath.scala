package net.mem_memov.binet.memory

trait UnusedPath(fail: String => Nothing) extends Path:

  override
  def isEmpty: Boolean =

    fail("unexpected")

  override
  def shorten: Either[String, Path.Split] =

    fail("unexpected")
