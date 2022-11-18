package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.element.general.reader.{ReadStock, ReadStore}
import net.mem_memov.binet.memory.specific.element.general.writer.{WriteStock, WriteStore}
import net.mem_memov.binet.memory.specific.element.specific.{Reader, Writer}

case class Element(
  storeOption: Option[Store],
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
    ReadStock[
      READER,
      Content,
      general.Split[Path],
      Stock
    ],
    ReadStore[
      READER,
      Content,
      general.Split[Path],
      Store
    ]
  ): general.element.Read[Element, Path, Content] with

    override
    def f(
      element: Element,
      origin: Path
    ): Either[String, Content] =

      for {
        pathSplit <- origin.shorten()
        content <-
          if pathSplit.rest.isEmpty then
            Right(reader.readStore(element.storeOption, pathSplit))
          else
            reader.readStock(element.stockOption, pathSplit)
      } yield content

  given [
    WRITER
  ](using
    writer: WRITER
  )(using
    WriteStock[
      WRITER,
      Content,
      general.Split[Path],
      Stock
    ],
    WriteStore[
      WRITER,
      Content,
      general.Split[Path],
      Store
    ]
  ): general.element.Write[Element, Path, Content] with

    override
    def f(
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


