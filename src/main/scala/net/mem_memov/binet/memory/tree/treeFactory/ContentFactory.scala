package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.TreeContent

trait ContentFactory:

  def makeContent(
    indices: Vector[UnsignedByte]
  )(using
    addressFactory: AddressFactory
  ): Content

object ContentFactory:

  def apply()(using
    blockFactory: BlockFactory
  ): ContentFactory =

    new ContentFactory:

      override
      def makeContent(
        indices: Vector[UnsignedByte]
      )(using
        addressFactory: AddressFactory
      ): Content =

        TreeContent(indices = indices)
