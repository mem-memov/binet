package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.{TreeBlock, TreeStore}

trait StoreFactory:

  lazy val emptyStore: Store

object StoreFactory:

  def apply()(
    using addressFactory: AddressFactory,
    blockFactory: BlockFactory
  ): StoreFactory =

    new StoreFactory:

      override lazy val emptyStore: Store =

        val blocks = Vector(blockFactory.emptyBlock)

        TreeStore(blocks)


