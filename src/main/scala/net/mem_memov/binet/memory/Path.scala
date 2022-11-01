package net.mem_memov.binet.memory

trait Path:

  def isEmpty: Boolean

  def shorten: Option[(UnsignedByte, Path)]