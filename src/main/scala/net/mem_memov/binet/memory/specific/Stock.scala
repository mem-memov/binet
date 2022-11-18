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

  given (using
    general.element.Read[Element, Path, Content]
  ): general.stock.Read[Stock, Content, Path] with

    override
    def f(
      stock: Stock,
      index: general.UnsignedByte,
      origin: Path
    ): Either[String, Content] =

      stock.elements(index.toInt).read(origin)

  given (using
    general.element.Write[Element, Path, Content]
  ): general.stock.Write[Stock, Content, Path] with

    override
    def f(
      stock: Stock,
      index: general.UnsignedByte,
      destination: Path,
      content: Content
    ): Either[String, Stock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)