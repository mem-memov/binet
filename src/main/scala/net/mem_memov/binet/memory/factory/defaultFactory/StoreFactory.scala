package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.store.DefaultStore

trait StoreFactory:
  
  def makeStore(size: Int): Store

object StoreFactory:

  val cachedFactory: Option[StoreFactory] = None
  
  def apply()(using blockFactory: BlockFactory): StoreFactory = cachedFactory.getOrElse {

    new StoreFactory:

      override def makeStore(size: Int): Store =

        val blocks = Vector.fill[Block](size)(DefaultBlock.empty)

        DefaultStore(blocks)
  }

