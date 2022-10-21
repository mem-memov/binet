package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory._

trait StockFactory:

  def makeStock(elements: Vector[Element]): Stock

object StockFactory:

  def apply(): StockFactory = (elements: Vector[Element]) => DefaultStock(elements)