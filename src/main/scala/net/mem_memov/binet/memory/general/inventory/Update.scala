package net.mem_memov.binet.memory.general.inventory

trait Update[INVENTORY, ADDRESS]:

  def updateInventory(
    inventory: INVENTORY,
    destination: ADDRESS,
    content: ADDRESS
  ): Either[String, INVENTORY]

  extension (inventory: INVENTORY)

    def update(
      destination: ADDRESS,
      content: ADDRESS
    ): Either[String, INVENTORY] =

      updateInventory(inventory, destination, content)
