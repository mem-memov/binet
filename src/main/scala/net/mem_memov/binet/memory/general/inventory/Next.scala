package net.mem_memov.binet.memory.general.inventory

trait Next[INVENTORY, ADDRESS]:

  private[Next]
  def f(
    inventory: INVENTORY
  ): ADDRESS

  extension (inventory: INVENTORY)

    def next(): ADDRESS =

      f(inventory)