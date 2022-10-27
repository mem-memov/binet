package net.mem_memov.binet.memory.live.defaultFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.live.defaultInventory._
import net.mem_memov.binet.memory.live._

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



