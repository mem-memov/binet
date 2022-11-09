package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeElement._
import net.mem_memov.binet.memory.tree.treeFactory.*

import scala.collection.immutable.Queue

case class TreeElement(
  storeOption: Option[Store],
  stockOption: Option[Stock]
)

object TreeElement:

  given (using
    writerService: Writer,
    readerService: Reader,
    elementFactory: ElementFactory
  ):Element[TreeElement] with

    override
    def write(
      destination: Path,
      content: Content
    ): Either[String, TreeElement] =

      for {
        pathSplit <- destination.shorten
        modifiedElement <-
          if pathSplit.rest.isEmpty then
            val updatedStore = writerService.writeStore(storeOption, pathSplit, content)
            Right(this.copy(storeOption = Some(updatedStore)))
          else
            for {
              updatedStock <- writerService.writeStock(stockOption, pathSplit, content)
            } yield this.copy(stockOption = Some(updatedStock))
      } yield modifiedElement

    override
    def read(
      origin: Path
    ): Either[String, Content] =

      for {
        pathSplit <- origin.shorten
        content <-
          if pathSplit.rest.isEmpty then
            readerService.readStore(storeOption, pathSplit)
          else
            readerService.readStock(stockOption, pathSplit)
      } yield content
