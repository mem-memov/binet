package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.element.DefaultElement
import net.mem_memov.binet.memory.level.DefaultLevel

trait StockFactory:

  def makeStock(size: Int, level: Level)(using elementFactory: ElementFactory): Stock

object StockFactory:

  def apply(): StockFactory =

    new StockFactory:

      override def makeStock(size: Int, level: Level)(using elementFactory: ElementFactory): Stock =

        val emptyElement = elementFactory.makeElement(level)

        val elements = Vector.fill[Element](DefaultLevel.size)(emptyElement)

        DefaultStock(elements)



