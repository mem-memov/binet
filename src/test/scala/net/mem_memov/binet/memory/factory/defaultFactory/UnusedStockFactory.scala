package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._

trait UnusedStockFactory(fail: String => Nothing) extends StockFactory:

  override def makeStock()(using elementFactory: ElementFactory): Stock =

    fail("unexpected")