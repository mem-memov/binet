package net.mem_memov.binet.memory.specific.specificElement.general.reader

import net.mem_memov.binet.memory.general.path.PathShortener.Split
import net.mem_memov.binet.memory.general.store

trait StoreReader[READER]:

  def readStoreOnPath[
    CONTENT,
    PATH,
    STORE : store.StoreReader
  ](
    reader: READER,
    storeOption: Option[STORE],
    pathSplit: Split[PATH]
  ): Either[String, CONTENT]
  
  extension (reader: READER)

    def readStore[
      CONTENT,
      PATH,
      STORE : store.StoreReader
    ](
      storeOption: Option[STORE],
      pathSplit: Split[PATH]
    ): Either[String, CONTENT] =

      readStoreOnPath(reader, storeOption, pathSplit)