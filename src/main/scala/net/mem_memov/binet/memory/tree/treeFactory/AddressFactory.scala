package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.address.defaultAddress._
import net.mem_memov.binet.memory.tree.TreeAddress

trait AddressFactory:

  def makeAddress(indices: List[UnsignedByte]): Address

  lazy val zeroAddress: Address

object AddressFactory:

  def apply(): AddressFactory =

    new AddressFactory:

      val ordering = Ordering()

      def makeAddress(indices: List[UnsignedByte]): Address =
        TreeAddress(indices, ordering)

      override lazy val zeroAddress: Address =
        makeAddress(List(UnsignedByte.minimum))


