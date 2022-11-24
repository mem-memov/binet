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

  given net_mem_memov_binet_memory_specific_Element_Read[CONTENT, PATH, READER](using
    ReadStock[READER, CONTENT, PATH, Stock],
    ReadStore[READER, CONTENT, PATH, Store],
    general.path.IsEmpty[PATH],
    general.path.Shorten[PATH]
  )(using
    reader: READER
  ): general.element.Read[Element, PATH, CONTENT] with

    override
    def f(
      element: Element,
      origin: PATH
    ): Either[String, CONTENT] =

      for {
        pathSplit <- origin.shorten()
        content <-
          if pathSplit.rest.isEmpty then
            Right(reader.readStore(element.storeOption, pathSplit))
          else
            reader.readStock(element.stockOption, pathSplit)
      } yield content

  given net_mem_memov_binet_memory_specific_Element_Write[CONTENT, PATH, WRITER](using
    WriteStock[WRITER, CONTENT, PATH, Stock],
    WriteStore[WRITER, CONTENT, PATH, Store],
    general.path.IsEmpty[PATH],
    general.path.Shorten[PATH]
  )(using
    writer: WRITER
  ): general.element.Write[Element, PATH, CONTENT] with

    override
    def f(
      element: Element,
      destination: PATH,
      content: CONTENT
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


