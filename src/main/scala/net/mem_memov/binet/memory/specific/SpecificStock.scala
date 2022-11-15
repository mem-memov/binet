package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class SpecificStock(
  elements: Vector[SpecificElement]
)

object SpecificStock:

  lazy val size: Int = general.UnsignedByte.maximum.toInt + 1

  def makeStock(): SpecificStock =

    val elements = Vector.fill[SpecificElement](size)(SpecificElement.emptyElement)

    SpecificStock(elements)

  given (using
    general.element.Read[SpecificElement, SpecificPath, SpecificContent]
  ): general.stock.Read[SpecificStock, SpecificContent, SpecificPath] with

    override
    def readStock(
      stock: SpecificStock,
      index: general.UnsignedByte,
      origin: SpecificPath
    ): Either[String, SpecificContent] =

      stock.elements(index.toInt).read(origin)

  given (using
    general.element.ElementWriter[SpecificElement, SpecificPath, SpecificContent]
  ): general.stock.Write[SpecificStock, SpecificContent, SpecificPath] with

    override
    def writeStock(
      stock: SpecificStock,
      index: general.UnsignedByte,
      destination: SpecificPath,
      content: SpecificContent
    ): Either[String, SpecificStock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)