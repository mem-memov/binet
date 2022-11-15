package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.PathShortener
import net.mem_memov.binet.memory.specific.{SpecificAddress, SpecificContent, SpecificPath, SpecificStock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.reader.{StockReader, StoreReader}
import net.mem_memov.binet.memory.general.address.AddressToContentConverter
import net.mem_memov.binet.memory.general.{stock, store}

class SpecificReader

object SpecificReader:

  given StockReader[SpecificReader] with

    override
    def readStockOnPath[
      CONTENT,
      PATH,
      STOCK : stock.StockReader
    ](
      reader: SpecificReader,
      stockOption: Option[SpecificStock],
      pathSplit: PathShortener.Split[SpecificPath]
    ): Either[String, SpecificContent] =

      val presentStock = stockOption.getOrElse(SpecificStock.makeStock())
      presentStock.read(pathSplit.index, pathSplit.rest)

  given (using
    store.StoreReader[SpecificStore],
    AddressToContentConverter[SpecificAddress]
  ): StoreReader[SpecificReader] with

    override
    def readStoreOnPath[
      CONTENT,
      PATH,
      STORE : store.StoreReader
    ](
      reader: SpecificReader,
      storeOption: Option[SpecificStore],
      pathSplit: PathShortener.Split[SpecificPath]
    ): Either[String, CONTENT] =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      Right(presentStore.read(pathSplit.index).toContent)
