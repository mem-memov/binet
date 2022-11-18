package net.mem_memov.binet.memory.specific.element.general.reader

import net.mem_memov.binet.memory.general

trait ReadStore[READER, CONTENT, PATH, STORE]:

  def f(
    reader: READER,
    storeOption: Option[STORE],
    pathSplit: general.Split[PATH]
  ): CONTENT

  extension (reader: READER)

    def readStore(
      storeOption: Option[STORE],
      pathSplit: general.Split[PATH]
    ): CONTENT =

      f(reader, storeOption, pathSplit)