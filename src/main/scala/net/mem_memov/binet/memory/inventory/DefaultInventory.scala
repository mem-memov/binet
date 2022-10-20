package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.{Address, Element, Inventory}

class DefaultInventory(nextAddress: Address, root: Element) extends Inventory:

  val next: Address = nextAddress

  def append(content: Address): Either[String, Inventory] =
    val trimmedContent = if content.isEmpty then Address.zero else content.trimBig
    if trimmedContent >= next && next != Address.zero then
      Left("Inventory not appended: content out of boundary")
    else
      for {
        rootWrite <- root.write(next, trimmedContent)
        newNext <- Right(next.increment)
      } yield Inventory(newNext, rootWrite.element)

  def update(destination: Address, content: Address): Either[String, Inventory] =
    val trimmedDestination = if content.isEmpty then Address.zero else destination.trimBig
    val trimmedContent = if content.isEmpty then Address.zero else content.trimBig
    if trimmedDestination >= next then
      Left("Inventory not appended: destination out of boundary")
    else
      if trimmedContent >= next then
        Left("Inventory not appended: content out of boundary")
      else
        for {
          rootWrite <- root.write(next, content)
        } yield Inventory(next, rootWrite.element)

  def read(origin: Address): Either[String, Address] =
    val trimmedOrigin = if origin.isEmpty then Address.zero else origin.trimBig
    for {
      content <- root.read(trimmedOrigin)
    } yield content.trimBig