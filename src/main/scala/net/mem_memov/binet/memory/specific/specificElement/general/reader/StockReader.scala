package net.mem_memov.binet.memory.specific.specificElement.general.reader

import net.mem_memov.binet.memory.general.path.PathShortener.Split
import net.mem_memov.binet.memory.general.stock

trait StockReader[READER]:

  def readStockOnPath[
    CONTENT,
    PATH,
    STOCK : stock.StockReader
  ](
    reader: READER,
    stockOption: Option[STOCK],
    pathSplit: Split[PATH]
  ): Either[String, CONTENT]

  extension (reader: READER)

    def readStock[
      CONTENT,
      PATH,
      STOCK : stock.StockReader
    ](
      stockOption: Option[STOCK],
      pathSplit: Split[PATH],
    ): Either[String, CONTENT] =

      readStockOnPath(reader, stockOption, pathSplit)