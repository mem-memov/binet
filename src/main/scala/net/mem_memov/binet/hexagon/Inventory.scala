package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

private[hexagon] class Inventory(
  val address: memory.Address,
  val entry: Entry,
  private val inventory1: memory.Inventory,
  private val inventory2: memory.Inventory,
  private val inventory3: memory.Inventory,
  private val inventory4: memory.Inventory,
  private val inventory5: memory.Inventory,
  private val inventory6: memory.Inventory,
):

  def append(entry: Entry): Task[Inventory] = ???

  def update(destination: memory.Address, content: Entry): Task[Inventory] = ???

  def read(origin: memory.Address): Entry = ???


