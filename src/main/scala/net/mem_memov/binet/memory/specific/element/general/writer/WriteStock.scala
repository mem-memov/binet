package net.mem_memov.binet.memory.specific.element.general.writer

trait WriteStock[WRITER, CONTENT, PATH_SPLIT, STOCK]:

  def f(
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

      f(writer, stockOption, pathSplit, content)
