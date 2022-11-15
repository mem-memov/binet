package net.mem_memov.binet.memory.general.stock

import net.mem_memov.binet.memory.general.UnsignedByte

trait StockReader[STOCK]:

  def readStock[
    CONTENT,
    PATH
  ](
    stock: STOCK,
    index: UnsignedByte,
    origin: PATH
  ): Either[String, CONTENT]

  extension (stock: STOCK)

    def read[
      CONTENT,
      PATH
    ](
      index: UnsignedByte,
      origin: PATH
    ): Either[String, CONTENT] =

      readStock(stock, index, origin)
