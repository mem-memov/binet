package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory.{Address, CompoundAddress, Element, Level, ShrinkableAddress, Store}

class StoreElement(level: Level, store: Store) extends Element:

  override
  def write(
    destination: ShrinkableAddress,
    content: CompoundAddress
  ): Either[String, Element] =

    destination.shorten match
      case None =>
        Left("Destination not written")
      case Some((index, rest)) =>
        if rest.isEmpty then
          for {
            padded <- level.padBig(content)
            updatedStore <- store.write(index, padded)
          } yield StoreElement(level, updatedStore)
        else
          val stockLevel = level.increment()
          val stock = stockLevel.createStock(store)
          for {
            updatedStock <- stock.write(index, rest, content)
            updatedStockElement <- StockElement(stockLevel, updatedStock).write(rest, content)
          } yield updatedStockElement

  override
  def read(
    origin: ShrinkableAddress
  ): Either[String, Address] =

    origin.shorten match
      case None =>
        Left("Origin not read")
      case Some((index, rest)) =>
        if rest.isEmpty then
          Right(store.read(index))
        else
          Left("Origin address too long")
