package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.TreeElement
import net.mem_memov.binet.memory.tree.treeElement.service.{ReaderService, WriterService}

trait ElementFactory:

  lazy val emptyElement: Element

  def create(
    store: Store,
    stock: Stock
  ): Element

object ElementFactory:

  def apply()(using StockFactory, StoreFactory): ElementFactory =

    new ElementFactory:

      given ElementFactory = this
      val writerService: WriterService = new WriterService()
      val readerService: ReaderService = new ReaderService()

      override
      lazy val emptyElement: Element =

        TreeElement(
          None,
          None,
          writerService,
          readerService
        )

      override
      def create(
        store: Store,
        stock: Stock
      ): Element =

        TreeElement(
          Some(store),
          Some(stock),
          writerService,
          readerService
        )

        
