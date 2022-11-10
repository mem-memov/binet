package net.mem_memov.binet.memoryOld.tree.treeInventory.argument.trimmer

import net.mem_memov.binet.memory.tree.treeInventory.argument.*
import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._

class TrimmerService extends Trimmer:

  def trim(
    address: Address
  )(using 
    addressFactory: AddressFactory
  ): Address =

    if address.isEmpty then addressFactory.zeroAddress else address.trimBig

