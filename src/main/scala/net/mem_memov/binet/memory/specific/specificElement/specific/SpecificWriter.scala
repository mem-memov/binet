package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.PathShortener
import net.mem_memov.binet.memory.specific.{SpecificContent, SpecificPath, SpecificStock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.writer.{StockWriter, StoreWriter}

class SpecificWriter

object SpecificWriter:

  given StockWriter[
    SpecificWriter,
    SpecificContent,
    PathShortener.Split[SpecificPath],
    SpecificStock
  ] with

    override
    def writeStockOnPath(
      writer: SpecificWriter,
      stockOption: Option[Any],
      pathSplit: PathShortener.Split[SpecificPath],
      content: SpecificContent
    ): Either[String, Any] =

      val presentStock = stockOption.getOrElse(SpecificStock.makeStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given StoreWriter[
    SpecificWriter,
    SpecificContent,
    PathShortener.Split[SpecificPath],
    SpecificStore
  ] with

    override
    def writeStoreOnPath(
      writer: SpecificWriter,
      storeOption: Option[SpecificStore],
      pathSplit: PathShortener.Split[SpecificPath],
      content: SpecificContent
    ): SpecificStore =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      presentStore.write(pathSplit.index, content)




