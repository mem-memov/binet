package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeInventory.*
import net.mem_memov.binet.memory.tree.*
import net.mem_memov.binet.memory.tree.treeInventory.argument.ArgumentService

trait InventoryFactory:

  lazy val emptyInventory: Inventory

object InventoryFactory:

  val argumentService: ArgumentService = ArgumentService()

  def apply()(using
    addressFactory: AddressFactory,
    elementFactory: ElementFactory
  ): InventoryFactory =

    new InventoryFactory:

      override lazy val emptyInventory: Inventory =

        TreeInventory(
          addressFactory.zeroAddress,
          elementFactory.emptyElement,
          argumentService
        )



