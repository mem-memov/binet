package net.mem_memov.binet.memory.general.inventory

trait InventoryReader[INVENTORY]:

  def readInventory[
    ADDRESS
  ](
    inventory: INVENTORY,
    origin: ADDRESS
  ): Either[String, ADDRESS]

  extension (inventory: INVENTORY)

    def read[
      ADDRESS
    ](
      origin: ADDRESS
    ): Either[String, ADDRESS] =

      readInventory(inventory, origin)
