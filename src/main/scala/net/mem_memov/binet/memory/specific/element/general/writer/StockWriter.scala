package net.mem_memov.binet.memory.specific.element.general.writer

trait StockWriter[WRITER, CONTENT, PATH_SPLIT, STOCK]:

  def writeStockOnPath(
    writer: WRITER,
    stockOption: Option[STOCK],
    pathSplit: PATH_SPLIT,
    content: CONTENT
  ): Either[String, STOCK]

  extension (writer: WRITER)

    def writeStock(
      stockOption: Option[STOCK],
      pathSplit: PATH_SPLIT,
      content: CONTENT
    ): Either[String, STOCK] =

      writeStockOnPath(writer, stockOption, pathSplit, content)
