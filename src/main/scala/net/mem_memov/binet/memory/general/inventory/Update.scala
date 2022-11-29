package net.mem_memov.binet.memory.general.inventory

trait Update[INVENTORY, ADDRESS]:

  def f(
    inventory: INVENTORY,
    destination: ADDRESS,
    content: ADDRESS
  ): Either[String, INVENTORY]

  extension (inventory: INVENTORY)

    def update(
      destination: ADDRESS,
      content: ADDRESS
    ): Either[String, INVENTORY] =

      f(inventory, destination, content)
