package net.mem_memov.binet.memory.general.inventory

trait InventoryUpdater[INVENTORY]:

  def updateInventory[
    ADDRESS
  ](
    inventory: INVENTORY,
    destination: ADDRESS,
    content: ADDRESS
  ): Either[String, INVENTORY]

  extension (inventory: INVENTORY)

    def update[
      ADDRESS
    ](
      destination: ADDRESS,
      content: ADDRESS
    ): Either[String, INVENTORY] =

      updateInventory(inventory, destination, content)
