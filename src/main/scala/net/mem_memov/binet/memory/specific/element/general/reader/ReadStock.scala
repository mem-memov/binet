package net.mem_memov.binet.memory.specific.element.general.reader

import net.mem_memov.binet.memory.general

trait ReadStock[READER, CONTENT, PATH, STOCK]:

  private[ReadStock]
  def f(
    stockOption: Option[STOCK],
    pathSplit: general.Split[PATH]
  ): Either[String, CONTENT]

  extension (reader: READER)

    def readStock(
      stockOption: Option[STOCK],
      pathSplit: general.Split[PATH]
    ): Either[String, CONTENT] =

      f(stockOption, pathSplit)