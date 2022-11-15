package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.*
import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.IncrementingResizer
import net.mem_memov.binet.memory.specific.specificAddress.general.orderer.ComparingOrderer

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
case class SpecificAddress(
  parts: List[general.UnsignedByte]
)

object SpecificAddress:

  def makeAddress(
    indices: List[general.UnsignedByte]
  ): SpecificAddress =

    SpecificAddress(indices)

  lazy val zeroAddress: SpecificAddress =

    SpecificAddress(List(general.UnsignedByte.minimum))

  given general.address.Indices[SpecificAddress] with

    override
    def indicesOfAddress(
      address: SpecificAddress
    ): List[general.UnsignedByte] =

      address.parts

  given general.address.Length[SpecificAddress] with

    override
    def lengthOfAddress(address: SpecificAddress): Int =

      address.parts.length

  given [
    RESIZER : DecrementingResizer
  ](using
    resizer: RESIZER
  ): general.address.Decrement[SpecificAddress] with

    override
    def decrementAddress(
      address: SpecificAddress
    ): Either[String, SpecificAddress] =

      resizer.decrement(address.parts).map(indices => address.copy(parts = indices))

  given [
    RESIZER: IncrementingResizer
  ](using
    resizer: RESIZER
  ): general.address.Increment[SpecificAddress] with

    override
    def incrementAddress(
      address: SpecificAddress
    ): SpecificAddress =

      address.copy(parts = resizer.increment(address.parts))

  given general.address.IsEmpty[SpecificAddress] with

    override
    def isAddressEmpty(
      address: SpecificAddress
    ): Boolean =

      address.parts.isEmpty

  given general.address.IsZero[SpecificAddress] with

    override
    def isAddressZero(
      address: SpecificAddress
    ): Boolean =

      address.parts.length == 1 && address.parts.head == general.UnsignedByte.minimum

  given ordering[
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    ComparingOrderer[ORDERER, SpecificAddress]
  ): Ordering[SpecificAddress] with

    override def compare(x: SpecificAddress, y: SpecificAddress): Int =

      orderer.compare(y, y)

  given general.address.ToString[SpecificAddress] with

    override
    def addressToString(
      address: SpecificAddress
    ): String =

      address.parts.map(_.toInt.toString()).mkString("Address(", ",", ")")

  given general.address.ToContent[SpecificAddress, SpecificContent] with

    override
    def addressToContent(
      address: SpecificAddress
    ): SpecificContent =

      SpecificContent(address.parts.reverse.toVector)

  given general.address.ToPath[SpecificAddress, SpecificPath] with

    override
    def addressToPath(
      address: SpecificAddress
    ): SpecificPath =

      SpecificPath(address.parts.reverse.toVector)


