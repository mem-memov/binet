package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.store.DefaultStore

trait StoreFactory:
  
  def makeStore(blocks: Vector[Block]): Store

object StoreFactory:
  
  def apply(): StoreFactory = (blocks: Vector[Block]) => DefaultStore(blocks)