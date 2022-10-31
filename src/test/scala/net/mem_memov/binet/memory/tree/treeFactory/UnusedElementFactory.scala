package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*

trait UnusedElementFactory(fail: String => Nothing) extends ElementFactory:

  override 
  lazy val emptyElement: Element =

    fail("unexpected")

  def create(
    store: Store,
    stock: Stock
  ): Element =

    fail("unexpected")