package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._

trait UnusedAddressFactory(fail: String => Nothing) extends AddressFactory:

  override def makeAddress(indices: List[UnsignedByte]): Address =

    fail("unexpected")

  override lazy val zeroAddress: Address =

    fail("unexpected")
