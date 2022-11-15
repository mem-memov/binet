package net.mem_memov.binet.memory.specific.element.general.reader

trait StockReader[READER, CONTENT, PATH_SPLIT, STOCK]:

  def readStockOnPath(
    reader: READER,
    stockOption: Option[STOCK],
    pathSplit: PATH_SPLIT
  ): Either[String, CONTENT]

  extension (reader: READER)

    def readStock(
      stockOption: Option[STOCK],
      pathSplit: PATH_SPLIT
    ): Either[String, CONTENT] =

      readStockOnPath(reader, stockOption, pathSplit)