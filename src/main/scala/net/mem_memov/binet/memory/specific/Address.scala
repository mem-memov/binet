package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.address.general.resizer.Decrement
import net.mem_memov.binet.memory.specific.address.general.resizer.Increment
import net.mem_memov.binet.memory.specific.address.general.orderer.Compare
import net.mem_memov.binet.memory.specific.address.general.padder.PadBig
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
case class Address(
  parts: List[general.UnsignedByte]
)

object Address:

  given [PADDER](using
    => PadBig[PADDER]
  )(using
    padder: PADDER
  ): general.address.PadBig[Address] with

    override
    def f(
      address: Address,
      target: Int
    ): Either[String, Address] =

      for {
        paddedParts <- padder.padBig(target, address.parts)
      } yield Address(paddedParts)

  given [TRIMMER](using
    => TrimBig[TRIMMER]
  )(using
    trimmer: TRIMMER
  ):general.address.TrimBig[Address] with

    override def f(address: Address): Address =

      Address(trimmer.trimBig(address.parts))

  given [RESIZER](using
    => Decrement[RESIZER]
  )(using
    resizer: RESIZER
  ): general.address.Decrement[Address] with

    override
    def f(
      address: Address
    ): Either[String, Address] =

      resizer.decrement(address.parts).map(indices => address.copy(parts = indices))

  given [RESIZER](using
    => Increment[RESIZER]
  )(using
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

  given [ORDERER](using
    => Compare[ORDERER, Address]
  )(using
    orderer: ORDERER
  ): Ordering[Address] with

    override def compare(x: Address, y: Address): Int =

      orderer.compare(y, y)

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

  given general.address.ToLittleEndian[Address] with

    override
    def f(
      address: Address
    ): Array[Byte] =

      address.parts.map(_.value).toArray

  given general.address.ToBigEndian[Address] with

    override
    def f(
      address: Address
    ): Array[Byte] =

      address.parts.reverse.map(_.value).toArray

