package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.element.{ElementReader, ElementWriter}
import net.mem_memov.binet.memory.general.stock.{StockReader, StockWriter}

case class SpecificStock(
  elements: Vector[SpecificElement]
)

object SpecificStock:

  lazy val size: Int = UnsignedByte.maximum.toInt + 1

  def makeStock(): SpecificStock =

    val elements = Vector.fill[SpecificElement](size)(SpecificElement.emptyElement)

    SpecificStock(elements)

  given (using
    ElementReader[SpecificElement]
  ): StockReader[SpecificStock] with

    override
    def readStock[
      CONTENT,
      PATH
    ](
      stock: SpecificStock,
      index: UnsignedByte,
      origin: PATH
    ): Either[String, CONTENT] =

      stock.elements(index.toInt).read(origin)

  given (using
    ElementWriter[SpecificElement]
  ): StockWriter[SpecificStock] with

    override
    def writeStock[
      CONTENT,
      PATH
    ](
      stock: SpecificStock,
      index: UnsignedByte,
      destination: PATH,
      content: CONTENT
    ): Either[String, SpecificStock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)