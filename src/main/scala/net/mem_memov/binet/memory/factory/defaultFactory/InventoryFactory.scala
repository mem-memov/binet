package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.inventory.DefaultInventory

trait InventoryFactory:

  def makeEmptyInventory(): DefaultInventory

object InventoryFactory:

  val cachedFactory: Option[InventoryFactory] = None

  def apply()(using
    addressFactory: AddressFactory,
    depthFactory: DepthFactory,
    elementFactory: ElementFactory
  ): InventoryFactory = cachedFactory.getOrElse {

    new InventoryFactory:

      override def makeEmptyInventory(): DefaultInventory =

        DefaultInventory(
          addressFactory.zeroAddress,
          elementFactory.rootElement,
          depthFactory.emptyDepth
        )
  }


