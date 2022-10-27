package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeInventory.*
import net.mem_memov.binet.memory.tree.*
import net.mem_memov.binet.memory.tree.treeInventory.argument.ArgumentService
import net.mem_memov.binet.memory.tree.treeInventory.argument.checker.CheckerService
import net.mem_memov.binet.memory.tree.treeInventory.argument.trimmer.TrimmerService

trait InventoryFactory:

  lazy val emptyInventory: Inventory

object InventoryFactory:

  def apply()(using
    addressFactory: AddressFactory,
    elementFactory: ElementFactory
  ): InventoryFactory =

    val argumentService: ArgumentService = ArgumentService(
      new CheckerService,
      new TrimmerService
    )

    new InventoryFactory:

      override lazy val emptyInventory: Inventory =

        TreeInventory(
          addressFactory.zeroAddress,
          elementFactory.emptyElement,
          argumentService
        )



