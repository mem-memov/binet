package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeInventory._
import net.mem_memov.binet.memory.tree._

trait InventoryFactory:

  lazy val emptyInventory: Inventory

object InventoryFactory:

  val argumentChecking: PreparingContent = PreparingContent()

  def apply()(using
    addressFactory: AddressFactory,
    elementFactory: ElementFactory
  ): InventoryFactory =

    new InventoryFactory:

      override lazy val emptyInventory: Inventory =

        TreeInventory(
          addressFactory.zeroAddress,
          elementFactory.emptyElement,
          argumentChecking
        )



