package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.TreeAddress

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
trait Address[A]:

  def indicesOfAddress(
    address: A
  ): List[UnsignedByte]

  def lengthOfAddress(
    address: A
  ): Int

  def incrementAddress(
    address: A
  ): A

  def decrementAddress(
    address: A
  ): Either[String, A]

  def isAddressZero(
    address: A
  ): Boolean

  override
  def addressToString(
    address: A
  ): String

  private[memory]
  def isAddressEmpty(
    address: A
  ): Boolean

  private[memory]
  def trimBigAddress(
    address: A
  ): A

  private[memory]
  def padBigAddress(
    address: A,
    target: Int
  ): Either[String, A]

  def canCompareWithAddress(
    address: A,
    that: A
  ): Boolean

  def isEqualToAddress(
    address: A,
    that: A
  ): Boolean

  def isGreaterThanAddress(
    address: A,
    that: A
  ): Boolean

  def isGreaterOrEqualToAddress(
    address: A,
    that: A
  ): Boolean

  def isLessThanAddress(
    address: A,
    that: A
  ): Boolean

  def isLessOrEqualToAddress(
    address: A,
    that: A
  ): Boolean

  def addressToPath(
    address: A
  ): Path

  def addressToContent(
    address: A
  ): Content

object Address:

  extension [A](address: A)(using a: Address[A])

    def indices: List[UnsignedByte] =

      a.indicesOfAddress(address)

    def length: Int =

      a.lengthOfAddress(address)

    def increment: A =

      a.incrementAddress(address)

    def decrement: Either[String, A] =

      a.decrementAddress(address)

    def isZero: Boolean =

      a.isAddressZero(address)

    override
    def toString: String =

      a.addressToString(address)

    private[memory]
    def isEmpty: Boolean =

      a.isAddressEmpty(address)

    private[memory]
    def trimBig: A =

      a.trimBigAddress(address)

    private[memory]
    def padBig(target: Int): Either[String, A] =

      a.padBigAddress(address, target)

    def canCompare(that: A): Boolean =

      a.canCompareWithAddress(address, that)

    def isEqual(that: A): Boolean =

      a.isEqualToAddress(address, that)

    def isGreater(that: A): Boolean =

      a.isGreaterThanAddress(address, that)

    def isGreaterOrEqual(that: A): Boolean =

      a.isGreaterOrEqualToAddress(address, that)

    def isLess(that: A): Boolean =

      a.isLessThanAddress(address, that)

    def isLessOrEqual(that: A): Boolean =

      a.isLessOrEqualToAddress(address, that)

    def toPath: Path =

      a.addressToPath(address)

    def toContent: Content =

      a.addressToContent(address)
