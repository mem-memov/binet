package net.mem_memov.binet.memory.live.defaultFactory

import net.mem_memov.binet.memory.*

trait UnusedAddressFactory(fail: String => Nothing) extends AddressFactory:

  override def makeAddress(indices: List[UnsignedByte]): Address =

    fail("unexpected")

  override lazy val zeroAddress: Address =

    fail("unexpected")
