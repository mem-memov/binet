package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.specificElement.general.reader.{StockReader, StoreReader}
import net.mem_memov.binet.memory.specific.specificElement.general.writer.{StockWriter, StoreWriter}
import net.mem_memov.binet.memory.specific.specificElement.specific.{SpecificReader, SpecificWriter}

case class Element(
  storeOption: Option[SpecificStore],
  stockOption: Option[Stock]
)

object Element:

  lazy val emptyElement: Element =

    Element(None, None)

  given [
    READER
  ](using
    reader: READER
  )(using
    StockReader[
      READER,
      Content,
      general.path.Shorten.Split[Path],
      Stock
    ],
    StoreReader[
      READER,
      Content,
      general.path.Shorten.Split[Path],
      SpecificStore
    ]
  ): general.element.Read[Element, Path, Content] with

    override
    def readElement(
      element: Element,
      origin: Path
    ): Either[String, Content] =

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
      Content,
      general.path.Shorten.Split[Path],
      Stock
    ],
    StoreWriter[
      WRITER,
      Content,
      general.path.Shorten.Split[Path],
      SpecificStore
    ]
  ): general.element.Write[Element, Path, Content] with

    override
    def writeElement(
      element: Element,
      destination: Path,
      content: Content
    ): Either[String, Element] =

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


