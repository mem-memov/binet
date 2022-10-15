package net.mem_memov.binet.memory

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 * Data represented by addresses is kept in a store.
 * The number of addresses in the store and the number of references to other elements in the stock are the same.
 * The level determines the number of parts of addresses in the store.
 */
trait Element:
  
  def write(
    destination: Address,
    content: Address
  ): Either[String, Element]

  def read(
    origin: Address
  ): Either[String, Address]

object Element:

  val root: Element = Element(Level.top, None, None)

  def apply(level: Level, store: Option[Store], stock: Option[Stock]): Element = new Element:

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