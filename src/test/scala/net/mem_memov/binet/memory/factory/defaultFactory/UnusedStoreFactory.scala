package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._

trait UnusedStoreFactory(fail: String => Nothing) extends StoreFactory:

  override lazy val emptyStore: Store =

    fail("unexpected")