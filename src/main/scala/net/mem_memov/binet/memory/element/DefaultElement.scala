package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory.{Address, Element, Level, Store, Stock}

class DefaultElement(level: Level, store: Option[Store], stock: Option[Stock]) extends Element:


  override
  def write(
    destination: Address,
    content: Address
  ): Either[String, Element] =

    destination.shorten match
      case None =>
        Left("Destination not written")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = store.getOrElse(level.createStore())
          for {
            padded <- level.padBig(content)
            updatedStore <- presentStore.write(index, padded)
          } yield Element(level, Option(updatedStore), stock)
        else
          val presentStock = stock.getOrElse(level.createStock())
          for {
            updatedStock <- presentStock.write(index, rest, content)
          } yield Element(level, store, Option(updatedStock))

  override
  def read(
    origin: Address
  ): Either[String, Address] =

    origin.shorten match
      case None =>
        Left("Origin not read")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = store.getOrElse(level.createStore())
          Right(presentStore.read(index))
        else
          val presentStock = stock.getOrElse(level.createStock())
          presentStock.read(index, rest)
