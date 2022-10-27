package net.mem_memov.binet.memory.tree.treeInventory.preparingContent

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory._

class PreparingContentService extends PreparingContent:

  override
  def checkAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    if !next.canCompare(content) then
      Left("Inventory not appended: wrong address type")
    else

      val trimmedContent = if content.isEmpty then addressFactory.zeroAddress else content.trimBig

      if next.isLessOrEqual(trimmedContent) && !next.isEqual(addressFactory.zeroAddress) then
        Left("Inventory not appended: content out of boundary")
      else
        Right(trimmedContent)
