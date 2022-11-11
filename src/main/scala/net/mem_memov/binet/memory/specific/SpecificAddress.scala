package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.*
import net.mem_memov.binet.memory.general.address.*
import net.mem_memov.binet.memory.general.address.AddressDecrementer
import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.IncrementingResizer
import net.mem_memov.binet.memory.specific.specificAddress.general.orderer.ComparingOrderer

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
case class SpecificAddress(
  parts: List[UnsignedByte]
)

object SpecificAddress:

  given AddressIndices[SpecificAddress] with

    override
    def indicesOfAddress(
      address: SpecificAddress
    ): List[UnsignedByte] =

      address.parts

  given AddressLength[SpecificAddress] with

    override
    def lengthOfAddress(address: SpecificAddress): Int =

      address.parts.length

  given [
    RESIZER : DecrementingResizer
  ](using
    resizer: RESIZER
  ): AddressDecrementer[SpecificAddress] with

    override
    def decrementAddress(
      address: SpecificAddress
    ): Either[String, SpecificAddress] =

      resizer.decrement(address.parts).map(indices => address.copy(parts = indices))

  given [
    RESIZER: IncrementingResizer
  ](using
    resizer: RESIZER
  ): AddressIncrementer[SpecificAddress] with

    override
    def incrementAddress(
      address: SpecificAddress
    ): SpecificAddress =

      address.copy(parts = resizer.increment(address.parts))

  given AddressEmptyChecker[SpecificAddress] with

    override
    def isAddressEmpty(
      address: SpecificAddress
    ): Boolean =

      address.parts.isEmpty

  given AddressZeroChecker[SpecificAddress] with

    override
    def isAddressZero(
      address: SpecificAddress
    ): Boolean =

      address.parts.length == 1 && address.parts.head == UnsignedByte.minimum

  given AddressComparabilityChecker[SpecificAddress] with

    override
    def canCompareWithAddress(
      address: SpecificAddress,
      that: SpecificAddress
    ): Boolean =

      true

  given [
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    ComparingOrderer[ORDERER, SpecificAddress]
  ): AddressEqualToChecker[SpecificAddress] with

    override
    def isEqualToAddress(
      address: SpecificAddress,
      that: SpecificAddress
    ): Boolean =

      orderer.compare(address, that) == 0

  given [
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    ComparingOrderer[ORDERER, SpecificAddress]
  ): AddressGreaterThanChecker[SpecificAddress] with

    override
    def isGreaterThanAddress(
      address: SpecificAddress,
      that: SpecificAddress
    ): Boolean =

      orderer.compare(address, that) > 0

  given [
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    ComparingOrderer[ORDERER, SpecificAddress]
  ): AddressGreaterThanOrEqualChecker[SpecificAddress] with

    override
    def isGreaterOrEqualToAddress(
      address: SpecificAddress,
      that: SpecificAddress
    ): Boolean =

      orderer.compare(address, that) >= 0

  given [
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    ComparingOrderer[ORDERER, SpecificAddress]
  ): AddressLessThanChecker[SpecificAddress] with

    override
    def isLessThanAddress(
      address: SpecificAddress,
      that: SpecificAddress
    ): Boolean =

      orderer.compare(address, that) < 0

  given [
    ORDERER
  ](using
    orderer: ORDERER
  )(using
    ComparingOrderer[ORDERER, SpecificAddress]
  ): AddressLessThanOrEqualChecker[SpecificAddress] with

    override
    def isLessOrEqualToAddress(
      address: SpecificAddress,
      that: SpecificAddress
    ): Boolean =

      orderer.compare(address, that) <= 0

  given AddressSerializer[SpecificAddress] with

    override
    def addressToString(
      address: SpecificAddress
    ): String =

      address.parts.map(_.toInt.toString()).mkString("Address(", ",", ")")

//  given AddressToContentConverter[SpecificAddress] with
//
//    override
//    def addressToContent(
//      address: SpecificAddress
//    ): CONTENT =

