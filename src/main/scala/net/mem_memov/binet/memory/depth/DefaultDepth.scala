package net.mem_memov.binet.memory.depth

import net.mem_memov.binet.memory.{Depth, Store}

case class DefaultDepth(number: Int) extends Depth:

  def expandStore(store: Store): Store =
    store.expand(number)
