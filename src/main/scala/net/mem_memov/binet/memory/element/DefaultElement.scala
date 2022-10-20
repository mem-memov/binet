package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory.{Address, Depth, Element, Level, Store, Stock}

case class DefaultElement(
  level: Level,
  storeOption: Option[Store],
  stockOption: Option[Stock]
) extends Element:

  override
  def write(
    destination: Address,
    content: Address
  ): Either[String, Element.Write] =

    destination.shorten match
      case None =>
        Left("Destination not written")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = storeOption.getOrElse(level.createStore())
          for {
            padded <- presentStore.padBig(content)
            updatedStore <- presentStore.write(index, padded)
          } yield
            val updatedElement = this.copy(storeOption = Some(updatedStore))
            Element.Write(updatedElement, level.toDepth)
        else
          val presentStock = stockOption.getOrElse(level.createStock())
          for {
            stockWrite <- presentStock.write(index, rest, content)
          } yield
            val presentStore = storeOption.getOrElse(level.createStore())
            val expandedStore = stockWrite.depth.expandStore(presentStore)
            val updatedElement = this.copy(storeOption = Option(expandedStore), stockOption = Some(stockWrite.stock))
            Element.Write(updatedElement, stockWrite.depth)

  override
  def read(
    origin: Address
  ): Either[String, Address] =

    origin.shorten match
      case None =>
        Left("Origin not read")
      case Some((index, rest)) =>
        if rest.isEmpty then
          val presentStore = storeOption.getOrElse(level.createStore())
          Right(presentStore.read(index))
        else
          val presentStock = stockOption.getOrElse(level.createStock())
          presentStock.read(index, rest)

object DefaultElement:

  val root: Element = DefaultElement(Level.top, None, None)