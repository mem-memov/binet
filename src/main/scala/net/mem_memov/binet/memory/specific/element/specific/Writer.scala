package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.general.writer.{WriteStock, WriteStore}

class Writer

object Writer:

  given (using
    general.stock.Write[specific.Stock, specific.Content, specific.Path]
  ): WriteStock[
    Writer,
    specific.Content,
    general.Split[specific.Path],
    specific.Stock
  ] with

    override
    def f(
      writer: Writer,
      stockOption: Option[specific.Stock],
      pathSplit: general.Split[specific.Path],
      content: specific.Content
    ): Either[String, specific.Stock] =

      val presentStock = stockOption.getOrElse(specific.Stock.emptyStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given (using
    general.store.Write[specific.Store, specific.Content]
  ): WriteStore[
    Writer,
    specific.Content,
    general.Split[specific.Path],
    specific.Store
  ] with

    override
    def f(
      writer: Writer,
      storeOption: Option[specific.Store],
      pathSplit: general.Split[specific.Path],
      content: specific.Content
    ): specific.Store =

      val presentStore = storeOption.getOrElse(specific.Store.emptyStore)
      presentStore.write(pathSplit.index, content)




