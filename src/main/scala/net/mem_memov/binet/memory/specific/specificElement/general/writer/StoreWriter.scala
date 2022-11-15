package net.mem_memov.binet.memory.specific.specificElement.general.writer

import net.mem_memov.binet.memory.general.path.PathShortener.Split
import net.mem_memov.binet.memory.general.store

trait StoreWriter[WRITER]:

  def writeStoreOnPath[
    CONTENT,
    PATH,
    STORE : store.StoreWriter
  ](
    writer: WRITER,
    storeOption: Option[STORE],
    pathSplit: Split[PATH],
    content: CONTENT
  ): STORE

  extension (writer: WRITER)

    def writeStore[
      CONTENT,
      PATH,
      STORE : store.StoreWriter
    ](
      storeOption: Option[STORE],
      pathSplit: Split[PATH],
      content: CONTENT
    ): STORE =

      writeStoreOnPath(writer, storeOption, pathSplit, content)
