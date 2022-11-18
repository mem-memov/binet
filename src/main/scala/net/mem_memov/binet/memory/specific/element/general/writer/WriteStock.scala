package net.mem_memov.binet.memory.specific.element.general.writer

import net.mem_memov.binet.memory.general

trait WriteStock[WRITER, CONTENT, PATH, STOCK]:

  def f(
    writer: WRITER,
    stockOption: Option[STOCK],
    pathSplit: general.Split[PATH],
    content: CONTENT
  ): Either[String, STOCK]

  extension (writer: WRITER)

    def writeStock(
      stockOption: Option[STOCK],
      pathSplit: general.Split[PATH],
      content: CONTENT
    ): Either[String, STOCK] =

      f(writer, stockOption, pathSplit, content)
