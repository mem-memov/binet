package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general.path.Shorten
import net.mem_memov.binet.memory.specific.{Content, Path, Stock, Store}
import net.mem_memov.binet.memory.specific.element.general.writer.{WriteStock, WriteStore}
import net.mem_memov.binet.memory.general.stock.Write as MemoryStockWriter
import net.mem_memov.binet.memory.general.store.Write as MemoryStoreWriter

class Writer

object Writer:

  given (using
    MemoryStockWriter[Stock, Content, Path]
  ): WriteStock[
    Writer,
    Content,
    Shorten.Split[Path],
    Stock
  ] with

    override
    def f(
      writer: Writer,
      stockOption: Option[Stock],
      pathSplit: Shorten.Split[Path],
      content: Content
    ): Either[String, Stock] =

      val presentStock = stockOption.getOrElse(Stock.makeStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given (using
    MemoryStoreWriter[Store, Content]
  ): WriteStore[
    Writer,
    Content,
    Shorten.Split[Path],
    Store
  ] with

    override
    def f(
      writer: Writer,
      storeOption: Option[Store],
      pathSplit: Shorten.Split[Path],
      content: Content
    ): Store =

      val presentStore = storeOption.getOrElse(Store.emptyStore)
      presentStore.write(pathSplit.index, content)




