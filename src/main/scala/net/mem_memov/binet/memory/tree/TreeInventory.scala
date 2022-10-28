package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory._

case class TreeInventory(
  next: Address,
  root: Element,
  argument: Argument
)(
  using addressFactory: AddressFactory
) extends Inventory:

  def append(content: Address): Either[String, TreeInventory] =

    for {
      trimmedContent <- argument.checkAndTrimPermissive(next, content)
      updatedRoot <- root.write(next, trimmedContent)
      newNext <- Right(next.increment)
    } yield this.copy(next = newNext, root = updatedRoot)

  def update(destination: Address, content: Address): Either[String, TreeInventory] =

    for {
      trimmedDestination <- argument.checkAndTrimRestrictive(next, destination)
      trimmedContent <- argument.checkAndTrimRestrictive(next, content)
      updatedRoot <- root.write(trimmedDestination, trimmedContent)
    } yield this.copy(root = updatedRoot)

  def read(origin: Address): Either[String, Address] =

    for {
      trimmedOrigin <- argument.checkAndTrimRestrictive(next, origin)
      content <- root.read(trimmedOrigin)
    } yield content.trimBig
