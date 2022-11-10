package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.memory.general.UnsignedByte

import scala.collection.immutable.Queue

trait UnusedStock(fail: String => Nothing) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Path,
    content: Content
  ): Either[String, Stock] =

    fail("unexpected")

  override
  def read(
    index: UnsignedByte,
    origin: Path
  ): Either[String, Content] =

    fail("unexpected")
