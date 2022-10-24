package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.element.DefaultElement

trait ElementFactory:

  lazy val emptyElement: Element

object ElementFactory:

  def apply()(using StockFactory, StoreFactory): ElementFactory =

    new ElementFactory:

      override lazy val emptyElement: Element =
        given ElementFactory = this
        DefaultElement(None, None)



