package net.mem_memov.binet.memory.specific.specificElement.general.reader

trait StoreReader[READER, CONTENT, PATH_SPLIT, STORE]:

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