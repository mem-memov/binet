package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeAddress.*
import net.mem_memov.binet.memory.tree.TreeAddress
import net.mem_memov.binet.memory.tree.treeAddress.orderer.OrdererService
import net.mem_memov.binet.memory.tree.treeAddress.resizer.ResizerService
import net.mem_memov.binet.memory.tree.treeAddress.resizer.service.{DecrementerService, IncrementerService}

trait AddressFactory:

  def makeAddress(indices: List[UnsignedByte]): Address

  lazy val zeroAddress: Address

object AddressFactory:

  def apply()(using
    contentFactory: ContentFactory,
    pathFactory: PathFactory
  ): AddressFactory =

    new AddressFactory:

      given AddressFactory = this

      val orderer = new OrdererService
      val resizer = new ResizerService(
        new IncrementerService,
        new DecrementerService
      )

      def makeAddress(indices: List[UnsignedByte]): Address =
        TreeAddress(indices, orderer, resizer)

      override lazy val zeroAddress: Address =
        makeAddress(List(UnsignedByte.minimum))


