package net.mem_memov.binet.memory.general.inventory

trait InventoryAppender[INVENTORY, ADDRESS]:

  def appendToInventory(
    inventory: INVENTORY,
    content: ADDRESS
  ): Either[String, INVENTORY]

  extension (inventory: INVENTORY)

    def append(
      content: ADDRESS
    ): Either[String, INVENTORY] =

      appendToInventory(inventory, content)
