package net.mem_memov.binet.memory.general.stock

import net.mem_memov.binet.memory.general.UnsignedByte

trait StockWriter[STOCK]:

  def writeStock[
    CONTENT, 
    PATH
  ](
    stock: STOCK,
    index: UnsignedByte,
    destination: PATH,
    content: CONTENT
  ): Either[String, STOCK]

  extension (stock: STOCK)

    def write[
      CONTENT,
      PATH
    ](
      index: UnsignedByte,
      destination: PATH,
      content: CONTENT
    ): Either[String, STOCK] =

      writeStock(stock, index, destination, content)
