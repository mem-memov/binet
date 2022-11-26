package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Stock(
  elements: Vector[Element]
)

object Stock:

  given [CONTENT, PATH](using
    => general.element.Read[Element, PATH, CONTENT]
  ): general.stock.Read[Stock, CONTENT, PATH] with

    override
    def f(
      stock: Stock,
      index: general.UnsignedByte,
      origin: PATH
    ): Either[String, CONTENT] =

      stock.elements(index.toInt).read(origin)

  given [CONTENT, PATH](using
    => general.element.Write[Element, PATH, CONTENT]
  ): general.stock.Write[Stock, CONTENT, PATH] with

    override
    def f(
      stock: Stock,
      index: general.UnsignedByte,
      destination: PATH,
      content: CONTENT
    ): Either[String, Stock] =

      val presentElement = stock.elements(index.toInt)
      for {

        updatedElement <- presentElement.write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)