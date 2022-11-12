package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.PathShortener
import net.mem_memov.binet.memory.specific.{SpecificContent, SpecificPath, SpecificStock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.reader.{StockReader, StoreReader}

class SpecificReader

object SpecificReader:

  given StockReader[
    SpecificReader,
    SpecificContent,
    PathShortener.Split[SpecificPath],
    SpecificStock
  ] with

    override
    def readStockOnPath(
      reader: SpecificReader,
      stockOption: Option[SpecificStock],
      pathSplit: PathShortener.Split[SpecificPath]
    ): Either[String, SpecificContent] =

      val presentStock = stockOption.getOrElse(SpecificStock.makeStock())
      presentStock.read(pathSplit.index, pathSplit.rest)

  given StoreReader[
    SpecificReader,
    SpecificContent,
    PathShortener.Split[SpecificPath],
    SpecificStore
  ] with

    override
    def readStoreOnPath(
      reader: SpecificReader,
      storeOption: Option[SpecificStore],
      pathSplit: PathShortener.Split[SpecificPath]
    ): Either[String, SpecificContent] =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      Right(presentStore.read(pathSplit.index).toContent)
