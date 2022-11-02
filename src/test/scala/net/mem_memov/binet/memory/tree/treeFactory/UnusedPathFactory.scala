package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*

trait UnusedPathFactory(fail: String => Nothing) extends PathFactory:

  override
  def create(
    indices: Vector[UnsignedByte]
  ): Path =

    fail("unexpected")
