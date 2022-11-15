package net.mem_memov.binet.memory.specific.specificElement.general.writer

import net.mem_memov.binet.memory.general.path.PathShortener.Split
import net.mem_memov.binet.memory.general.stock

trait StockWriter[WRITER]:

  def writeStockOnPath[
    CONTENT,
    PATH,
    STOCK : stock.StockWriter
  ](
    writer: WRITER,
    stockOption: Option[STOCK],
    pathSplit: Split[PATH],
    content: CONTENT
  ): Either[String, STOCK]

  extension (writer: WRITER)

    def writeStock[
      CONTENT,
      PATH,
      STOCK : stock.StockWriter
    ](
      stockOption: Option[STOCK],
      pathSplit: Split[PATH],
      content: CONTENT
    ): Either[String, STOCK] =

      writeStockOnPath(writer, stockOption, pathSplit, content)
