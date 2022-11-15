package net.mem_memov.binet.memory.general.inventory

trait Slice[INVENTORY]:

  def foreachSliceInInventory(
    inventory: INVENTORY,
    f: Array[Byte] => Unit
  ): Unit

  extension (inventory: INVENTORY)

    def foreachSlice(
      f: Array[Byte] => Unit
    ): Unit =

      foreachSliceInInventory(inventory, f)
