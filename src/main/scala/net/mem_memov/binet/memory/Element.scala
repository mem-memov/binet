package net.mem_memov.binet.memory

import scala.annotation.tailrec

private[memory] class Element(
  private val level: Level
):

  private lazy val store: Store = level.createStore
  private lazy val stock: Stock = level.createStock

  def write(destination: Address, content: Address): Boolean =
    destination.shorten match
      case None =>
        false
      case Some((destinationPart, shorterDestination)) =>
        if shorterDestination.isEmpty then
          level.padBig(content) match
            case None =>
              false
            case Some(paddedContent) =>
              store.write(destinationPart, paddedContent)
        else
          stock.write(destinationPart, shorterDestination, content)

  def read(origin: Address): Option[Address] =
    origin.shorten match
      case None =>
        None
      case Some((originPart, shorterOrigin)) =>
        if shorterOrigin.isEmpty then
          Some(store.read(originPart))
        else
          stock.read(originPart, shorterOrigin)
