package net.mem_memov.binet.memory.general.inventory

trait Slice[INVENTORY]:

  def f(
    inventory: INVENTORY,
    process: Array[Byte] => Unit
  ): Unit

  extension (inventory: INVENTORY)

    def foreachSlice(
      process: Array[Byte] => Unit
    ): Unit =

      f(inventory, process)
