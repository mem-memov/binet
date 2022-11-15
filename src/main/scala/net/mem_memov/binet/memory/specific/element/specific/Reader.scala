package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general.path.Shorten
import net.mem_memov.binet.memory.specific.{Address, Content, Path, Stock, Store}
import net.mem_memov.binet.memory.specific.element.general.reader.{ReadStock, ReadStore}
import net.mem_memov.binet.memory.general.stock.Read as MemoryStockReader
import net.mem_memov.binet.memory.general.store.Read as MemoryStoreReader
import net.mem_memov.binet.memory.general.address.ToContent

class Reader

object Reader:

  given (using
    MemoryStockReader[Stock, Content, Path]
  ): ReadStock[
    Reader,
    Content,
    Shorten.Split[Path],
    Stock
  ] with

    override
    def readStockOnPath(
      reader: Reader,
      stockOption: Option[Stock],
      pathSplit: Shorten.Split[Path]
    ): Either[String, Content] =

      val presentStock = stockOption.getOrElse(Stock.makeStock())
      presentStock.read(pathSplit.index, pathSplit.rest)

  given (using
    MemoryStoreReader[Store, Address],
    ToContent[Address, Content]
  ): ReadStore[
    Reader,
    Content,
    Shorten.Split[Path],
    Store
  ] with

    override
    def readStoreOnPath(
      reader: Reader,
      storeOption: Option[Store],
      pathSplit: Shorten.Split[Path]
    ): Either[String, Content] =

      val presentStore = storeOption.getOrElse(Store.emptyStore)
      Right(presentStore.read(pathSplit.index).toContent)
