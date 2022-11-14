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
    ElementReader[SpecificElement, SpecificPath, SpecificContent]
  ): StockReader[SpecificStock, SpecificContent, SpecificPath] with

    override
    def readStock(
      stock: SpecificStock,
      index: UnsignedByte,
      origin: SpecificPath
    ): Either[String, SpecificContent] =

      stock.elements(index.toInt).read(origin)

  given (using
    ElementWriter[SpecificElement, SpecificPath, SpecificContent]
  ): StockWriter[SpecificStock, SpecificContent, SpecificPath] with

    override
    def writeStock(
      stock: SpecificStock,
      index: UnsignedByte,
      destination: SpecificPath,
      content: SpecificContent
    ): Either[String, SpecificStock] =

      for {
        updatedElement <- stock.elements(index.toInt).write(destination, content)
        updatedElements <- Right(stock.elements.updated(index.toInt, updatedElement))
      } yield stock.copy(elements = updatedElements)