package net.mem_memov.binet.memory.tree.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.{DefaultElement, DefaultStock}

trait StockFactory:

  def makeStock()(using elementFactory: ElementFactory): Stock

object StockFactory:

  lazy val size: Int = UnsignedByte.maximum.toInt + 1

  def apply(): StockFactory =

    new StockFactory:

      override def makeStock()(using elementFactory: ElementFactory): Stock =

        val elements = Vector.fill[Element](size)(elementFactory.emptyElement)

        DefaultStock(elements)



