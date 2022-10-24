package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.element.DefaultElement

trait StockFactory:

  def makeStock()(using elementFactory: ElementFactory): Stock

object StockFactory:

  def apply(): StockFactory =

    new StockFactory:

      override def makeStock()(using elementFactory: ElementFactory): Stock =

        val elements = Vector.fill[Element](DefaultStock.size)(elementFactory.emptyElement)

        DefaultStock(elements)



