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
  def read(
    origin: Address
  ): Either[String, Address] =
    
    fail("unexpected")

  override
  def enqueueStock(
    queue: Queue[Element]
  ): Queue[Element] =

    fail("unexpected")

  override
  def foreachSlice(
    f: Array[Byte] => Unit
  ): Unit =

    fail("unexpected")

  override
  def withStore(
    store: Store
  ): Element =

    fail("unexpected")