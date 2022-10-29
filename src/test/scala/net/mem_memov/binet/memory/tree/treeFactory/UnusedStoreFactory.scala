package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*

trait UnusedStoreFactory(fail: String => Nothing) extends StoreFactory:

  override 
  lazy val emptyStore: Store =

    fail("unexpected")