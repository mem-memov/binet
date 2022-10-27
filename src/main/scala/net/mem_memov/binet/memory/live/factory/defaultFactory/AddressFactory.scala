package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.address.defaultAddress._
import net.mem_memov.binet.memory.live.DefaultAddress

trait AddressFactory:

  def makeAddress(indices: List[UnsignedByte]): Address

  lazy val zeroAddress: Address

object AddressFactory:

  def apply(): AddressFactory =

    new AddressFactory:

      val ordering = Ordering()

      def makeAddress(indices: List[UnsignedByte]): Address =
        DefaultAddress(indices, ordering)

      override lazy val zeroAddress: Address =
        makeAddress(List(UnsignedByte.minimum))


