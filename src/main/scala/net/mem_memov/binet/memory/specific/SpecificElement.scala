package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.element.{ElementReader, ElementWriter}
import net.mem_memov.binet.memory.general.path.PathShortener
import net.mem_memov.binet.memory.specific.specificElement.general.reader.{StockReader, StoreReader}
import net.mem_memov.binet.memory.specific.specificElement.general.writer.{StockWriter, StoreWriter}
import net.mem_memov.binet.memory.specific.specificElement.specific.{SpecificReader, SpecificWriter}

case class SpecificElement(
  storeOption: Option[SpecificStore],
  stockOption: Option[SpecificStock]
)

object SpecificElement:

  lazy val emptyElement: SpecificElement =

    SpecificElement(None, None)

  given [
    READER
  ](using
    reader: READER
  )(using
    StockReader[
      READER,
      SpecificContent,
      PathShortener.Split[SpecificPath],
      SpecificStock
    ],
    StoreReader[
      READER,
      SpecificContent,
      PathShortener.Split[SpecificPath],
      SpecificStore
    ]
  ): ElementReader[SpecificElement, SpecificPath, SpecificContent] with

    override
    def readElement(
      element: SpecificElement,
      origin: SpecificPath
    ): Either[String, SpecificContent] =

      for {
        pathSplit <- origin.shorten()
        content <-
          if pathSplit.rest.isEmpty then
            reader.readStore(element.storeOption, pathSplit)
          else
            reader.readStock(element.stockOption, pathSplit)
      } yield content

  given [
    WRITER
  ](using
    writer: WRITER
  )(using
    StockWriter[
      WRITER,
      SpecificContent,
      PathShortener.Split[SpecificPath],
      SpecificStock
    ],
    StoreWriter[
      WRITER,
      SpecificContent,
      PathShortener.Split[SpecificPath],
      SpecificStore
    ]
  ): ElementWriter[SpecificElement, SpecificPath, SpecificContent] with

    override
    def writeElement(
      element: SpecificElement,
      destination: SpecificPath,
      content: SpecificContent
    ): Either[String, SpecificElement] =

      for {
        pathSplit <- destination.shorten()
        modifiedElement <-
          if pathSplit.rest.isEmpty then
            val updatedStore = writer.writeStore(element.storeOption, pathSplit, content)
            Right(element.copy(storeOption = Some(updatedStore)))
          else
            for {
              updatedStock <- writer.writeStock(element.stockOption, pathSplit, content)
            } yield element.copy(stockOption = Some(updatedStock))
      } yield modifiedElement


