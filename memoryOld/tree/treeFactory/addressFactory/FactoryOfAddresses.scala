package net.mem_memov.binet.memoryOld.tree.treeFactory.addressFactory

import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.TreeAddress
import net.mem_memov.binet.memory.tree.treeAddress.formatter.FormatterService
import net.mem_memov.binet.memory.tree.treeAddress.formatter.padder.PadderService
import net.mem_memov.binet.memory.tree.treeAddress.formatter.trimmer.TrimmerService
import net.mem_memov.binet.memory.tree.treeAddress.resizer.ResizerService
import net.mem_memov.binet.memory.tree.treeAddress.resizer.service.{DecrementerService, IncrementerService}
import net.mem_memov.binet.memory.tree.treeFactory.{AddressFactory, ContentFactory, PathFactory}

object FactoryOfAddresses:

  given addressFactory(using
    contentFactory: ContentFactory,
    pathFactory: PathFactory
  ):AddressFactory[TreeAddress] =

    new AddressFactory:

      given AddressFactory[TreeAddress] = this

      val trimmer = new TrimmerService
      val formatter = new FormatterService(
        new PadderService(trimmer),
        trimmer
      )

      val resizer = new ResizerService(
        new IncrementerService,
        new DecrementerService
      )

      def makeAddress(indices: List[UnsignedByte]): TreeAddress =
        TreeAddress(indices, formatter, resizer, contentFactory, pathFactory)

      override lazy val zeroAddress: TreeAddress =
        makeAddress(List(UnsignedByte.minimum))
