package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

private[hexagon] class Inventory(
  private val inventory1: memory.Inventory,
  private val inventory2: memory.Inventory,
  private val inventory3: memory.Inventory,
  private val inventory4: memory.Inventory,
  private val inventory5: memory.Inventory,
  private val inventory6: memory.Inventory,
):

  def append(entry: Entry): Option[memory.Address] = ???

  def update(destination: memory.Address, content: Entry): Boolean = ???

  def read(origin: memory.Address): Option[Entry] = ???


