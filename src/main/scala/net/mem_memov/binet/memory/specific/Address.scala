package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.specific.address.general.resizer.*
import net.mem_memov.binet.memory.specific.address.general.padder.PadBig
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig
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

  given net_mem_memov_binet_memory_specific_Address_PadBig[
    PADDER
  ](using
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

  given net_mem_memov_binet_memory_specific_Address_TrimBig[
    TRIMMER
  ](using
    => TrimBig[TRIMMER]
  )(using
    trimmer: TRIMMER
  ):general.address.TrimBig[Address] with

    override def f(address: Address): Address =

      Address(trimmer.trimBig(address.parts))

  given net_mem_memov_binet_memory_specific_Address_Decrement[
    RESIZER
  ](using
    => Decrement[RESIZER]
  )(using
    resizer: RESIZER
  ): general.address.Decrement[Address] with

    override
    def f(
      address: Address
    ): Either[String, Address] =

      resizer.decrement(address.parts).map(indices => address.copy(parts = indices))

  given net_mem_memov_binet_memory_specific_Address_Increment[
    RESIZER
  ](using
    => Increment[RESIZER]
  )(using
    resizer: RESIZER
  ): general.address.Increment[Address] with

    override
    def f(
      address: Address
    ): Address =

      address.copy(parts = resizer.increment(address.parts))

  given net_mem_memov_binet_memory_specific_Address_IsEmpty: general.address.IsEmpty[Address] with

    override
    def f(
      address: Address
    ): Boolean =

      address.parts.isEmpty

  given net_mem_memov_binet_memory_specific_Address_IsZero: general.address.IsZero[Address] with

    override
    def f(
      address: Address
    ): Boolean =

      address.parts.length == 1 && address.parts.head == general.UnsignedByte.minimum

  given net_mem_memov_binet_memory_specific_Address_Ordering[
    ORDERER
  ](using
    => Compare[ORDERER, Address]
  )(using
    orderer: ORDERER
  ): Ordering[Address] with

    override def compare(x: Address, y: Address): Int =

      orderer.compare(y, y)

  given net_mem_memov_binet_memory_specific_Address_ToString: general.address.ToString[Address] with

    override
    def f(
      address: Address
    ): String =

      address.parts.map(_.toInt.toString()).mkString("Address(", ",", ")")

  given net_mem_memov_binet_memory_specific_Address_ToContent: general.address.ToContent[Address, Content] with

    override
    def f(
      address: Address
    ): Content =

      Content(address.parts.reverse.toVector)

  given net_mem_memov_binet_memory_specific_Address_ToPath: general.address.ToPath[Address, Path] with

    override
    def f(
      address: Address
    ): Path =

      Path(address.parts.reverse.toVector)


