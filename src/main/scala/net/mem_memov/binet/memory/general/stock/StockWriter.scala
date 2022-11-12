package net.mem_memov.binet.memory.general.stock

import net.mem_memov.binet.memory.general.UnsignedByte

trait StockWriter[STOCK, CONTENT, PATH]:

  def writeStock(
    stock: STOCK,
    index: UnsignedByte,
    destination: PATH,
    content: CONTENT
  ): Either[String, STOCK]

  extension (stock: STOCK)

    def write(
      index: UnsignedByte,
      destination: PATH,
      content: CONTENT
    ): Either[String, STOCK] =

      writeStock(stock, index, destination, content)
