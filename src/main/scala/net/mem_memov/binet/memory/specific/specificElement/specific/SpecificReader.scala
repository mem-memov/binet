package net.mem_memov.binet.memory.specific.specificElement.specific

import net.mem_memov.binet.memory.general.path.Shorten
import net.mem_memov.binet.memory.specific.{Address, Content, Path, Stock, SpecificStore}
import net.mem_memov.binet.memory.specific.specificElement.general.reader.{StockReader, StoreReader}
import net.mem_memov.binet.memory.general.stock.Read as MemoryStockReader
import net.mem_memov.binet.memory.general.store.Read as MemoryStoreReader
import net.mem_memov.binet.memory.general.address.ToContent

class SpecificReader

object SpecificReader:

  given (using
    MemoryStockReader[Stock, Content, Path]
  ): StockReader[
    SpecificReader,
    Content,
    Shorten.Split[Path],
    Stock
  ] with

    override
    def readStockOnPath(
      reader: SpecificReader,
      stockOption: Option[Stock],
      pathSplit: Shorten.Split[Path]
    ): Either[String, Content] =

      val presentStock = stockOption.getOrElse(Stock.makeStock())
      presentStock.read(pathSplit.index, pathSplit.rest)

  given (using
    MemoryStoreReader[SpecificStore, Address],
    ToContent[Address, Content]
  ): StoreReader[
    SpecificReader,
    Content,
    Shorten.Split[Path],
    SpecificStore
  ] with

    override
    def readStoreOnPath(
      reader: SpecificReader,
      storeOption: Option[SpecificStore],
      pathSplit: Shorten.Split[Path]
    ): Either[String, Content] =

      val presentStore = storeOption.getOrElse(SpecificStore.emptyStore)
      Right(presentStore.read(pathSplit.index).toContent)
