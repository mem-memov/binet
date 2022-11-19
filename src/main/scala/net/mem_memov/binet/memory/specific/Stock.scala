package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Stock(
  elements: Vector[Element]
)

object Stock:

  lazy val size: Int = general.UnsignedByte.maximum.toInt + 1

  def emptyStock(): Stock =

    val elements = Vector.fill[Element](size)(Element.emptyElement)

    Stock(elements)

  given [CONTENT, PATH](using
    general.element.Read[Element, PATH, CONTENT]
  ): general.stock.Read[Stock, CONTENT, PATH] with

    override
    def f(
      stock: Stock,
      index: general.UnsignedByte,
      origin: PATH
    ): Either[String, CONTENT] =

      stock.elements(index.toInt).read(origin)

  given [CONTENT, PATH](using
    general.element.Write[Element, PATH, CONTENT]
  ): general.stock.Write[Stock, CONTENT, PATH] with

    override
    def f(
      stock: Stock,
      index: general.UnsignedByte,
      destination: PATH,
      content: CONTENT
    ): Either[String, Stock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)