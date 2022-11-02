package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*

trait UnusedContentFactory(fail: String => Nothing) extends ContentFactory:

  override
  def makeContent(
    indices: Vector[UnsignedByte]
  )(using
    addressFactory: AddressFactory
  ): Content =

    fail("unexpected")
