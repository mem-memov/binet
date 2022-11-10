package net.mem_memov.binet.memoryOld.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.*
import net.mem_memov.binet.memory.tree.TreeAddress
import net.mem_memov.binet.memory.tree.treeAddress.formatter.FormatterService
import net.mem_memov.binet.memory.tree.treeAddress.formatter.padder.PadderService
import net.mem_memov.binet.memory.tree.treeAddress.formatter.trimmer.TrimmerService
import net.mem_memov.binet.memory.tree.treeAddress.orderer.OrdererService
import net.mem_memov.binet.memory.tree.treeAddress.resizer.ResizerService
import net.mem_memov.binet.memory.tree.treeAddress.resizer.service.{DecrementerService, IncrementerService}

trait AddressFactory[A]:

  def makeAddress(indices: List[UnsignedByte]): A

  lazy val zeroAddress: A


