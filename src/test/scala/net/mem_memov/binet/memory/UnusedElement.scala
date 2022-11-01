package net.mem_memov.binet.memory

import scala.collection.immutable.Queue

trait UnusedElement(fail: String => Nothing) extends Element:

  override
  def write(
    destination: Address,
    content: Address
  ): Either[String, Element] =
    
    fail("unexpected")

  override
  def write(
    destination: Path,
    content: Content
  ): Either[String, Element] =

    fail("unexpected")

  override
  def read(
    origin: Address
  ): Either[String, Address] =
    
    fail("unexpected")

  override
  def read(
    origin: Path
  ): Either[String, Content] =

    fail("unexpected")