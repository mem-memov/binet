package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.PathShortener
import net.mem_memov.binet.memory.general.{stock, store}
import net.mem_memov.binet.memory.specific.{SpecificContent, SpecificPath, SpecificStock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.writer.{StockWriter, StoreWriter}

class SpecificWriter

object SpecificWriter:

  given StockWriter[SpecificWriter] with

    override
    def writeStockOnPath[
      CONTENT,
      PATH,
      STOCK : stock.StockWriter
    ](
      writer: SpecificWriter,
      stockOption: Option[SpecificStock],
      pathSplit: PathShortener.Split[SpecificPath],
      content: SpecificContent
    ): Either[String, SpecificStock] =

      val presentStock = stockOption.getOrElse(SpecificStock.makeStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given StoreWriter[SpecificWriter] with

    override
    def writeStoreOnPath[
      CONTENT,
      PATH,
      STORE : store.StoreWriter
    ](
      writer: SpecificWriter,
      storeOption: Option[SpecificStore],
      pathSplit: PathShortener.Split[SpecificPath],
      content: CONTENT
    ): SpecificStore =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      presentStore.write(pathSplit.index, content)




