package net.mem_memov.binet.memory.tree.treeAddress

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.TreeAddress

trait Orderer:

  def compare(left: Address, right: Address): Int

