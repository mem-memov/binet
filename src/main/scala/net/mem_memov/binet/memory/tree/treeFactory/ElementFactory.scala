package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.TreeElement

trait ElementFactory:

  lazy val emptyElement: Element

  def create(
    store: Store,
    stock: Stock
  ): Element

object ElementFactory:

  def apply()(using StockFactory, StoreFactory): ElementFactory =

    new ElementFactory:

      override
      lazy val emptyElement: Element =
        given ElementFactory = this
        TreeElement(None, None)

      override
      def create(
        store: Store,
        stock: Stock
      ): Element =
        given ElementFactory = this
        TreeElement(Some(store), Some(stock))

        
