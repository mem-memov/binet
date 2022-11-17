package net.mem_memov.binet.memory.specific.element.general.reader

trait ReadStock[READER, CONTENT, PATH_SPLIT, STOCK]:

  def f(
    reader: READER,
    stockOption: Option[STOCK],
    pathSplit: PATH_SPLIT
  ): Either[String, CONTENT]

  extension (reader: READER)

    def readStock(
      stockOption: Option[STOCK],
      pathSplit: PATH_SPLIT
    ): Either[String, CONTENT] =

      f(reader, stockOption, pathSplit)