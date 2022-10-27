package net.mem_memov.binet.memory.tree.defaultFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.defaultInventory._
import net.mem_memov.binet.memory.tree._

trait InventoryFactory:

  lazy val emptyInventory: Inventory

object InventoryFactory:

  val argumentChecking: ArgumentChecking = ArgumentChecking()

  def apply()(using
    addressFactory: AddressFactory,
    elementFactory: ElementFactory
  ): InventoryFactory =

    new InventoryFactory:

      override lazy val emptyInventory: Inventory =

        DefaultInventory(
          addressFactory.zeroAddress,
          elementFactory.emptyElement,
          argumentChecking
        )



