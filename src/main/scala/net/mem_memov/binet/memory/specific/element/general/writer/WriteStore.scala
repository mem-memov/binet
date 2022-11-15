package net.mem_memov.binet.memory.specific.element.general.writer

trait WriteStore[WRITER, CONTENT, PATH_SPLIT, STORE]:

  def writeStoreOnPath(
    writer: WRITER,
    storeOption: Option[STORE],
    pathSplit: PATH_SPLIT,
    content: CONTENT
  ): STORE

  extension (writer: WRITER)

    def writeStore(
      storeOption: Option[STORE],
      pathSplit: PATH_SPLIT,
      content: CONTENT
    ): STORE =

      writeStoreOnPath(writer, storeOption, pathSplit, content)
