package net.mem_memov.binet.memory.general.stock

import net.mem_memov.binet.memory.general.UnsignedByte

trait Write[STOCK, CONTENT, PATH]:

  private[Write]
  def f(
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

      f(stock, index, destination, content)
