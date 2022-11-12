package net.mem_memov.binet.memoryOld.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.TreePath

trait PathFactory:

  def create(
    indices: Vector[UnsignedByte]
  ): Path


object PathFactory:

  def apply(): PathFactory =

    new PathFactory:

      override def create(indices: Vector[UnsignedByte]): Path =

        TreePath(indices = indices)