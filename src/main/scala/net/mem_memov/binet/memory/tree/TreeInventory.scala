package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory._

case class TreeInventory(
  next: Address,
  root: Element,
  argumentChecking: ArgumentChecking
)(
  using addressFactory: AddressFactory
) extends Inventory:

  def append(content: Address): Either[String, TreeInventory] =

    for {
      trimmedContent <- argumentChecking.checkContentAndTrim(next, content)
      updatedRoot <- root.write(next, trimmedContent)
      newNext <- Right(next.increment)
    } yield this.copy(next = newNext, root = updatedRoot)

  def update(destination: Address, content: Address): Either[String, TreeInventory] =

    if !next.canCompare(destination) || !next.canCompare(content) then
      Left("Inventory not updated: wrong address type")
    else

      val trimmedDestination = if content.isEmpty then addressFactory.zeroAddress else destination.trimBig
      val trimmedContent = if content.isEmpty then addressFactory.zeroAddress else content.trimBig

      if trimmedDestination.isGreaterOrEqual(next) then
        Left("Inventory not updated: destination out of boundary")
      else
        if trimmedContent.isGreaterOrEqual(next) then
          Left("Inventory not updated: content out of boundary")
        else
          for {
            updatedRoot <- root.write(next, content)
          } yield this.copy(root = updatedRoot)

  def read(origin: Address): Either[String, Address] =

    if !next.canCompare(origin) then
      Left("Inventory reading failed: wrong address type")
    else

      val trimmedOrigin = if origin.isEmpty then addressFactory.zeroAddress else origin.trimBig
      if trimmedOrigin.isGreaterOrEqual(next) then
        Left("Inventory reading failed: origin out of boundary")
      else
        for {
          content <- root.read(trimmedOrigin)
        } yield content.trimBig
