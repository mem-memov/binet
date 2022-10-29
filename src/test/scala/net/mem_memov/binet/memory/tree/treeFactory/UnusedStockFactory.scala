package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*

trait UnusedStockFactory(fail: String => Nothing) extends StockFactory:

  override 
  def makeStock()(using elementFactory: ElementFactory): Stock =

    fail("unexpected")