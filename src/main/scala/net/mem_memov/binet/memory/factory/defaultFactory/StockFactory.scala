package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.element.DefaultElement
import net.mem_memov.binet.memory.level.DefaultLevel

trait StockFactory:

  def makeStock(size: Int, level: Level): Stock

object StockFactory:

  val cachedFactory: Option[StockFactory] = None

  def apply()(using elementFactory: ElementFactory): StockFactory = cachedFactory.getOrElse {

    new StockFactory:

      override def makeStock(size: Int, level: Level): Stock =

        val emptyElement = elementFactory.makeElement(level)

        val elements = Vector.fill[Element](DefaultLevel.size)(emptyElement)

        DefaultStock(elements)
  }


