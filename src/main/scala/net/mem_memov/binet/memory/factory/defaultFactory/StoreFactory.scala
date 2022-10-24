package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.store.DefaultStore

trait StoreFactory:
  
  def makeStore(): Store

object StoreFactory:

  def apply()(using blockFactory: BlockFactory): StoreFactory =

    new StoreFactory:

      override def makeStore(): Store =

        val blocks = Vector(blockFactory.emptyBlock)

        DefaultStore(blocks)


