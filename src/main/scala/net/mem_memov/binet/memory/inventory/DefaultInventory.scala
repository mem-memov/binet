package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.element.DefaultElement
import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

case class DefaultInventory(
  next: Address,
  root: Element
)(
  using addressFactory: AddressFactory
) extends Inventory:

  def append(content: Address): Either[String, DefaultInventory] =

    val trimmedContent = if content.isEmpty then addressFactory.zeroAddress else content.trimBig

    if trimmedContent >= next && next != addressFactory.zeroAddress then
      Left("Inventory not appended: content out of boundary")
    else
      for {
        updatedRoot <- root.write(next, trimmedContent)
        newNext <- Right(next.increment)
      } yield this.copy(next = newNext, root = updatedRoot)

  def update(destination: Address, content: Address): Either[String, DefaultInventory] =

    val trimmedDestination = if content.isEmpty then addressFactory.zeroAddress else destination.trimBig
    val trimmedContent = if content.isEmpty then addressFactory.zeroAddress else content.trimBig

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

    val trimmedOrigin = if origin.isEmpty then addressFactory.zeroAddress else origin.trimBig
    if trimmedOrigin >= next then
      Left("Reading failed: origin out of boundary")
    else
      for {
        content <- root.read(trimmedOrigin)
      } yield content.trimBig
