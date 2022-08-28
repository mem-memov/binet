package net.mem_memov.binet.memory

import zio.*

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 */
private[memory] class Element(
  private val level: Level,
  private val store: Option[Store],
  private val stock: Option[Stock]
):

  def write(destination: Address, content: Address): Task[Element] =

    for {
      indexAndRest <- destination.shorten
      updatedElement <- indexAndRest match
        case None =>
          ZIO.fail(Exception("Destination not written"))
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

    } yield updatedElement

  def read(origin: Address): Task[Address] =

    for {
      indexAndRest <- origin.shorten
      content <- indexAndRest match
        case None =>
          ZIO.fail(Exception("Origin not read"))
        case Some((index, rest)) =>
          if rest.isEmpty then
            val presentStore = store.getOrElse(level.createStore)
            ZIO.succeed(presentStore.read(index))
          else
            val presentStock = stock.getOrElse(level.createStock)
            presentStock.read(index, rest)
    } yield content

object Element:

  val root: Element = Element.root

  def apply(level: Level, store: Option[Store], stock: Option[Stock]): Element =
    new Element(level, store, stock)