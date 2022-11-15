package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.Shorten
import net.mem_memov.binet.memory.specific.{SpecificContent, SpecificPath, SpecificStock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.writer.{StockWriter, StoreWriter}
import net.mem_memov.binet.memory.general.stock.Write as MemoryStockWriter
import net.mem_memov.binet.memory.general.store.Write as MemoryStoreWriter

class SpecificWriter

object SpecificWriter:

  given (using
    MemoryStockWriter[SpecificStock, SpecificContent, SpecificPath]
  ): StockWriter[
    SpecificWriter,
    SpecificContent,
    Shorten.Split[SpecificPath],
    SpecificStock
  ] with

    override
    def writeStockOnPath(
      writer: SpecificWriter,
      stockOption: Option[SpecificStock],
      pathSplit: Shorten.Split[SpecificPath],
      content: SpecificContent
    ): Either[String, SpecificStock] =

      val presentStock = stockOption.getOrElse(SpecificStock.makeStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given (using
    MemoryStoreWriter[SpecificStore, SpecificContent]
  ): StoreWriter[
    SpecificWriter,
    SpecificContent,
    Shorten.Split[SpecificPath],
    SpecificStore
  ] with

    override
    def writeStoreOnPath(
      writer: SpecificWriter,
      storeOption: Option[SpecificStore],
      pathSplit: Shorten.Split[SpecificPath],
      content: SpecificContent
    ): SpecificStore =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      presentStore.write(pathSplit.index, content)




