package net.mem_memov.binet.memory.specific.element.general.reader

trait ReadStore[READER, CONTENT, PATH_SPLIT, STORE]:

  def readStoreOnPath(
    reader: READER,
    storeOption: Option[STORE],
    pathSplit: PATH_SPLIT
  ): Either[String, CONTENT]
  
  extension (reader: READER)

    def readStore(
      storeOption: Option[STORE],
      pathSplit: PATH_SPLIT
    ): Either[String, CONTENT] =

      readStoreOnPath(reader, storeOption, pathSplit)