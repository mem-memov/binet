package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.Shorten
import net.mem_memov.binet.memory.specific.{Content, Path, Stock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.writer.{StockWriter, StoreWriter}
import net.mem_memov.binet.memory.general.stock.Write as MemoryStockWriter
import net.mem_memov.binet.memory.general.store.Write as MemoryStoreWriter

class SpecificWriter

object SpecificWriter:

  given (using
    MemoryStockWriter[Stock, Content, Path]
  ): StockWriter[
    SpecificWriter,
    Content,
    Shorten.Split[Path],
    Stock
  ] with

    override
    def writeStockOnPath(
      writer: SpecificWriter,
      stockOption: Option[Stock],
      pathSplit: Shorten.Split[Path],
      content: Content
    ): Either[String, Stock] =

      val presentStock = stockOption.getOrElse(Stock.makeStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given (using
    MemoryStoreWriter[SpecificStore, Content]
  ): StoreWriter[
    SpecificWriter,
    Content,
    Shorten.Split[Path],
    SpecificStore
  ] with

    override
    def writeStoreOnPath(
      writer: SpecificWriter,
      storeOption: Option[SpecificStore],
      pathSplit: Shorten.Split[Path],
      content: Content
    ): SpecificStore =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      presentStore.write(pathSplit.index, content)




