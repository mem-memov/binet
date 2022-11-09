package net.mem_memov.binet.memory.tree.treeFactory.inventoryFactory

import net.mem_memov.binet.memory.{Address, Element}
import net.mem_memov.binet.memory.tree.treeFactory.InventoryFactory
import net.mem_memov.binet.memory.tree.{TreeAddress, TreeInventory}
import net.mem_memov.binet.memory.tree.treeInventory.argument.ArgumentService
import net.mem_memov.binet.memory.tree.treeInventory.argument.checker.CheckerService
import net.mem_memov.binet.memory.tree.treeInventory.argument.trimmer.TrimmerService
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.Argument

object FactoryOfInventories:

  given inventoryFactory(using
    addressFactory: AddressFactory[TreeAddress],
    elementFactory: ElementFactory,
    traversalFactory: TraversalFactory
  ): InventoryFactory[TreeInventory] with

    lazy val emptyInventory: TreeInventory =

      val argumentService: ArgumentService = ArgumentService(
        new CheckerService,
        new TrimmerService
      )

      TreeInventory(
        addressFactory.zeroAddress,
        elementFactory.emptyElement,
        argumentService,
        traversalFactory
      )
