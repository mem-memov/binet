package net.mem_memov.binet.memory.general.inventory

trait Read[INVENTORY, ADDRESS]:

  def readInventory(
    inventory: INVENTORY,
    origin: ADDRESS
  ): Either[String, ADDRESS]

  extension (inventory: INVENTORY)

    def read(
      origin: ADDRESS
    ): Either[String, ADDRESS] =

      readInventory(inventory, origin)
