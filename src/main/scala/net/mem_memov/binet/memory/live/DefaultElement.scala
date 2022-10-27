package net.mem_memov.binet.memory.live

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.live.defaultFactory._

case class DefaultElement(
  storeOption: Option[Store],
  stockOption: Option[Stock]
)(using
  elementFactory: ElementFactory,
  stockFactory: StockFactory,
  storeFactory: StoreFactory
) extends Element:

  override
  def write(
    destination: Address,
    content: Address
  ): Either[String, DefaultElement] =

    destination.shorten match
      case None =>
        Left("Destination not written")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = storeOption.getOrElse(storeFactory.emptyStore)
          for {
            expandedStore <- Right(content.expandStore(presentStore))
            updatedStore <- expandedStore.write(index, content)
          } yield this.copy(storeOption = Some(updatedStore))
        else
          val presentStock = stockOption.getOrElse(stockFactory.makeStock())
          for {
            updatedStock <- presentStock.write(index, rest, content)
          } yield this.copy(stockOption = Some(updatedStock))

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
