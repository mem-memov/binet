package net.mem_memov.binet.memory.specific.element.general.reader

trait ReadStore[READER, CONTENT, PATH_SPLIT, STORE]:

  def f(
    reader: READER,
    storeOption: Option[STORE],
    pathSplit: PATH_SPLIT
  ): Either[String, CONTENT]
  
  extension (reader: READER)

    def readStore(
      storeOption: Option[STORE],
      pathSplit: PATH_SPLIT
    ): Either[String, CONTENT] =

      f(reader, storeOption, pathSplit)