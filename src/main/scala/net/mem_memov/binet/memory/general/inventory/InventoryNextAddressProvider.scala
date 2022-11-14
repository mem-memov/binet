package net.mem_memov.binet.memory.general.inventory

trait InventoryNextAddressProvider[INVENTORY, ADDRESS]:

  def nextInInventory(
    inventory: INVENTORY
  ): ADDRESS

  extension (inventory: INVENTORY)

    def next(): ADDRESS =

      nextInInventory(inventory)