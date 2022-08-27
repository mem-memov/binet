package net.mem_memov.binet.memory

import scala.annotation.tailrec

class Inventory:

  val start: Address = new Address(List(UnsignedByte.minimum))

  private var next: Address = start
  private lazy val root: Element = new Element(new Level)

  def append(content: Address): Option[Address] =
    val trimmedContent = content.trimBig
    if trimmedContent > next then
      None
    else
      if root.write(next, trimmedContent) then
        val destination = next
        next = next.increment
        Some(destination)
      else
        None

  def update(destination: Address, content: Address): Boolean =
    val trimmedDestination = destination.trimBig
    val trimmedContent = content.trimBig
    if trimmedDestination > next || trimmedContent > trimmedDestination then
      false
    else
      root.write(next, content)

  def read(origin: Address): Option[Address] =
    root.read(origin).map(_.trimBig)

  def foreach(f: Address => Unit): Unit =
    @tailrec
    def walk(f: Address => Unit, source: Address): Unit =
      if source != next then
        read(source).foreach(f)
        walk(f, source.increment)
    walk(f, start)
