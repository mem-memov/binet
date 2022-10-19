package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory.{Address, CompoundAddress, Element, Level, ShrinkableAddress, Stock}

class StockElement(level: Level, stock: Stock) extends Element:

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
          Left("Destination address too short")
        else
          for {
            updatedStock <- stock.write(index, rest, content)
          } yield StockElement(level, updatedStock)

  override
  def read(
    origin: ShrinkableAddress
  ): Either[String, Address] =

    origin.shorten match
      case None =>
        Left("Origin not read")
      case Some((index, rest)) =>
        if rest.isEmpty then
          Left("Origin address too short")
        else
          ???