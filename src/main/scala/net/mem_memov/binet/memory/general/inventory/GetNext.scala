package net.mem_memov.binet.memory.general.inventory

trait GetNext[INVENTORY, ADDRESS]:

  def f(
    inventory: INVENTORY
  ): ADDRESS

  extension (inventory: INVENTORY)

    def getNext(): ADDRESS =

      f(inventory)