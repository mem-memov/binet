package net.mem_memov.binet.memory.tree.treeAddress

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.TreeAddress

trait Orderer[O]:

  def compareAddresses[A : Address](
    oderer: O,
    left: A,
    right: A
  ): Int

  extension (orderer: O)

    def compare[A : Address](
      left: A,
      right: A
    ): Int =

      compareAddresses(orderer, left, right)
