package net.mem_memov.binet.memory.specific.element.general.writer

import net.mem_memov.binet.memory.general

trait WriteStore[WRITER, CONTENT, PATH, STORE]:

  private[WriteStore]
  def f(
    storeOption: Option[STORE],
    pathSplit: general.Split[PATH],
    content: CONTENT
  ): STORE

  extension (writer: WRITER)

    def writeStore(
      storeOption: Option[STORE],
      pathSplit: general.Split[PATH],
      content: CONTENT
    ): STORE =

      f(storeOption, pathSplit, content)
