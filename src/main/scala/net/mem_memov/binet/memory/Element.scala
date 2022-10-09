package net.mem_memov.binet.memory

import zio.stm._

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 */
private[memory] class Element(
  private val level: Level,
  private val store: Option[Store],
  private val stock: Option[Stock]
):

  def write(destination: Address, content: Address): STM[String, Unit] =

    destination.shorten match
      case None =>
        STM.fail("Destination not written")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = store.getOrElse(level.createStore)
          for {
            padded <- level.padBig(content)
            updatedStore <- presentStore.write(index, padded)
          } yield Element(level, Option(updatedStore), stock)
        else
          val presentStock = stock.getOrElse(level.createStock)
          for {
            updatedStock <- presentStock.write(index, rest, content)
          } yield Element(level, store, Option(updatedStock))


  def read(origin: Address): STM[String, Address] =

    origin.shorten match
      case None =>
        Left(Exception("Origin not read"))
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = store.getOrElse(level.createStore)
          Right(presentStore.read(index))
        else
          val presentStock = stock.getOrElse(level.createStock)
          presentStock.read(index, rest)

object Element:

  val root: Element = Element.root

  def apply(level: Level, store: Option[Store], stock: Option[Stock]): USTM[Element] =
    STM.succeed(new Element(level, store, stock))