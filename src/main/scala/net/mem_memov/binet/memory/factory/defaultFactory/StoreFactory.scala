package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.store.DefaultStore

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

        DefaultStore(blocks)


