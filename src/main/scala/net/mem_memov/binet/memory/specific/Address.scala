package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.specific.address.general.resizer.*
import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.address.general.resizer.Increment
import net.mem_memov.binet.memory.specific.address.general.orderer.Compare

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
case class Address(
  parts: List[general.UnsignedByte]
)

object Address:

  given general.address.Length[Address] with

    override
    def f(address: Address): Int =

      address.parts.length

  given [
    RESIZER : Decrement
  ](using
    resizer: RESIZER
  ): general.address.Decrement[Address] with

    override
    def f(
      address: Address
    ): Either[String, Address] =

      resizer.decrement(address.parts).map(indices => address.copy(parts = indices))

  given [
    RESIZER: Increment
  ](using
    resizer: RESIZER
  ): general.address.Increment[Address] with

    override
    def f(
      address: Address
    ): Address =

      address.copy(parts = resizer.increment(address.parts))

  given general.address.IsEmpty[Address] with

    override
    def f(
      address: Address
    ): Boolean =

      address.parts.isEmpty

  given general.address.IsZero[Address] with

    override
    def f(
      address: Address
    ): Boolean =

      address.parts.length == 1 && address.parts.head == general.UnsignedByte.minimum

  given ordering[
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    Compare[ORDERER, Address]
  ): Ordering[Address] with

    override def compare(x: Address, y: Address): Int =

      orderer.compare(y, y)

  given general.address.ToString[Address] with

    override
    def f(
      address: Address
    ): String =

      address.parts.map(_.toInt.toString()).mkString("Address(", ",", ")")

  given general.address.ToContent[Address, Content] with

    override
    def f(
      address: Address
    ): Content =

      Content(address.parts.reverse.toVector)

  given general.address.ToPath[Address, Path] with

    override
    def f(
      address: Address
    ): Path =

      Path(address.parts.reverse.toVector)


