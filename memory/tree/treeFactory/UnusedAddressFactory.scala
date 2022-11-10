package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait UnusedAddressFactory(fail: String => Nothing) extends AddressFactory:

  override
  def makeAddress(
    indices: List[UnsignedByte]
  ): Address =

    fail("unexpected")

  override
  lazy val zeroAddress: Address =

    fail("unexpected")
