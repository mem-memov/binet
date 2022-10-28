package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._

case class TreeElement(
  storeOption: Option[Store],
  stockOption: Option[Stock],
  nextOption: Option[Element]
)(using
  elementFactory: ElementFactory,
  stockFactory: StockFactory,
  storeFactory: StoreFactory
) extends Element:

  override
  def write(
    destination: Address,
    content: Address
  ): Either[String, (TreeElement, Option[Element])] =

    destination.shorten match
      case None =>
        Left("Destination not written")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = storeOption.getOrElse(storeFactory.emptyStore)
          for {
            expandedStore <- Right(content.expandStore(presentStore))
            modifiedStore <- expandedStore.write(index, content)
          } yield
            val modifiedElement = this.copy(storeOption = Some(modifiedStore))
            val initiatedElementOption = if storeOption.isEmpty then Some(modifiedElement) else None
            (modifiedElement, initiatedElementOption)
        else
          val presentStock = stockOption.getOrElse(stockFactory.makeStock())
          for {
            writeResult <- presentStock.write(index, rest, content)
            (modifiedStock, initiatedElementOption) = writeResult
          } yield
            val modifiedElement = this.copy(stockOption = Some(modifiedStock))
            (modifiedElement, initiatedElementOption)

  override
  def read(
    origin: Address
  ): Either[String, Address] =

    origin.shorten match
      case None =>
        Left("Origin not read")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = storeOption.getOrElse(storeFactory.emptyStore)
          Right(presentStore.read(index))
        else
          val presentStock = stockOption.getOrElse(stockFactory.makeStock())
          presentStock.read(index, rest)
