package net.mem_memov.binet.memory.general.stock

import net.mem_memov.binet.memory.general.UnsignedByte

trait Read[STOCK, CONTENT, PATH]:

  def readStock(
    stock: STOCK,
    index: UnsignedByte,
    origin: PATH
  ): Either[String, CONTENT]

  extension (stock: STOCK)

    def read(
      index: UnsignedByte,
      origin: PATH
    ): Either[String, CONTENT] =

      readStock(stock, index, origin)
