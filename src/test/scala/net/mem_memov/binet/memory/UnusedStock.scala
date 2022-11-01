package net.mem_memov.binet.memory

import scala.collection.immutable.Queue

trait UnusedStock(fail: String => Nothing) extends Stock:

  override
  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock] =
    
    fail("unexpected")

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
    origin: Address
  ): Either[String, Address] =
    
    fail("unexpected")

  override
  def read(
    index: UnsignedByte,
    origin: Path
  ): Either[String, Content] =

    fail("unexpected")
