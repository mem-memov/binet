package net.mem_memov.binet.memory.general.inventory

trait InventoryAppender[INVENTORY]:

  def appendToInventory[
    ADDRESS
  ](
    inventory: INVENTORY,
    content: ADDRESS
  ): Either[String, INVENTORY]

  extension (inventory: INVENTORY)

    def append[
      ADDRESS
    ](
      content: ADDRESS
    ): Either[String, INVENTORY] =

      appendToInventory(inventory, content)
