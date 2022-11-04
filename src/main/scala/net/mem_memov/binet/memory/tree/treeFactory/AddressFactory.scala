package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeAddress.*
import net.mem_memov.binet.memory.tree.TreeAddress
import net.mem_memov.binet.memory.tree.treeAddress.formatter.FormatterService
import net.mem_memov.binet.memory.tree.treeAddress.formatter.padder.PadderService
import net.mem_memov.binet.memory.tree.treeAddress.formatter.trimmer.TrimmerService
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

      val trimmer = new TrimmerService
      val formatter = new FormatterService(
        new PadderService(trimmer),
        trimmer
      )
      val orderer = new OrdererService(formatter)
      val resizer = new ResizerService(
        new IncrementerService,
        new DecrementerService
      )

      def makeAddress(indices: List[UnsignedByte]): Address =
        TreeAddress(indices, formatter, orderer, resizer)

      override lazy val zeroAddress: Address =
        makeAddress(List(UnsignedByte.minimum))


