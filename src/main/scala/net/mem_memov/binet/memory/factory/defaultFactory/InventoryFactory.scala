package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.inventory.DefaultInventory

trait InventoryFactory:

  lazy val emptyInventory: Inventory

object InventoryFactory:

  def apply()(using
    addressFactory: AddressFactory,
    elementFactory: ElementFactory
  ): InventoryFactory =

    new InventoryFactory:

      override lazy val emptyInventory: Inventory =

        DefaultInventory(
          addressFactory.zeroAddress,
          elementFactory.emptyElement
        )



