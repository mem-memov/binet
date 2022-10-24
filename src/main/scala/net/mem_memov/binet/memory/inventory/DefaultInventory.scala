package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.element.DefaultElement
import net.mem_memov.binet.memory.factory.DefaultFactory
import net.mem_memov.binet.memory._

case class DefaultInventory(next: Address, root: Element) extends Inventory:

  def append(content: Address): Either[String, Inventory] =
    val trimmedContent = if content.isEmpty then DefaultAddress.zero else content.trimBig
    if trimmedContent >= next && next != DefaultAddress.zero then
      Left("Inventory not appended: content out of boundary")
    else
      for {
        updatedRoot <- root.write(next, trimmedContent)
        newNext <- Right(next.increment)
      } yield this.copy(next = newNext, root = updatedRoot)

  def update(destination: Address, content: Address): Either[String, Inventory] =
    val trimmedDestination = if content.isEmpty then DefaultAddress.zero else destination.trimBig
    val trimmedContent = if content.isEmpty then DefaultAddress.zero else content.trimBig
    if trimmedDestination >= next then
      Left("Inventory not appended: destination out of boundary")
    else
      if trimmedContent >= next then
        Left("Inventory not appended: content out of boundary")
      else
        for {
          updatedRoot <- root.write(next, content)
        } yield this.copy(root = updatedRoot)

  def read(origin: Address): Either[String, Address] =
    val trimmedOrigin = if origin.isEmpty then DefaultAddress.zero else origin.trimBig
    for {
      content <- root.read(trimmedOrigin)
    } yield content.trimBig

object DefaultInventory:

  val start: Address = DefaultAddress.zero