package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.PathShortener
import net.mem_memov.binet.memory.specific.{SpecificAddress, SpecificContent, SpecificPath, SpecificStock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.reader.{StockReader, StoreReader}
import net.mem_memov.binet.memory.general.stock.StockReader as MemoryStockReader
import net.mem_memov.binet.memory.general.store.StoreReader as MemoryStoreReader
import net.mem_memov.binet.memory.general.address.AddressToContentConverter

class SpecificReader

object SpecificReader:

  given (using
    MemoryStockReader[SpecificStock, SpecificContent, SpecificPath]
  ): StockReader[
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

  given (using
    MemoryStoreReader[SpecificStore, SpecificAddress],
    AddressToContentConverter[SpecificAddress, SpecificContent]
  ): StoreReader[
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
