package net.mem_memov.binet.memory

trait Path:

  def isEmpty: Boolean

  def shorten: Either[String, Path.Split]

object Path:

  case class Split(index: UnsignedByte, rest: Path)