package net.mem_memov.binet.memoryOld.tree.treeInventory.argument

import net.mem_memov.binet.memory.Address
import net.mem_memov.binet.memory.tree.treeFactory.AddressFactory

trait Trimmer:

  def trim(
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Address
