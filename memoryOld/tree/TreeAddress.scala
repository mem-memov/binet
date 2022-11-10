package net.mem_memov.binet.memoryOld.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.*
import net.mem_memov.binet.memory.tree.treeAddress.orderer.OrdererService
import net.mem_memov.binet.memory.tree.treeFactory.{AddressFactory, ContentFactory, PathFactory}

case class TreeAddress(
  parts: List[UnsignedByte],
  formatter: Formatter,
  resizer: Resizer,
  contentFactory: ContentFactory,
  pathFactory: PathFactory
)

object TreeAddress:

  given (using
    orderer: Orderer[TreeAddress],
    addressFactory: AddressFactory[TreeAddress]
  ):Address[TreeAddress] with

    override
    def indicesOfAddress(
      address: TreeAddress
    ): List[UnsignedByte] =

      address.parts

    override
    def lengthOfAddress(
      address: TreeAddress
    ): Int =

      address.parts.length

    override
    def incrementAddress(
      address: TreeAddress
    ): TreeAddress =

      address.copy(parts = address.resizer.increment(address.parts))

    override
    def decrementAddress(
      address: TreeAddress
    ): Either[String, TreeAddress] =

      address.resizer.decrement(address.parts).map(indices => address.copy(parts = indices))

    override
    def isAddressZero(
      address: TreeAddress
    ): Boolean =

      address.parts.length == 1 && address.parts.head == UnsignedByte.minimum

    override
    def addressToString(
      address: TreeAddress
    ): String =

      address.parts.map(_.toInt.toString()).mkString("Address(", ",", ")")

    private[memory]
    override
    def isAddressEmpty(
      address: TreeAddress
    ): Boolean =

      address.parts.isEmpty

    private[memory]
    override
    def trimBigAddress(
      address: TreeAddress
    ): TreeAddress =

      address.copy(parts = address.formatter.trimBig(address.parts))

    private[memory]
    override
    def padBigAddress(
      address: TreeAddress,
      target: Int
    ): Either[String, TreeAddress] =

      address.formatter.padBig(target, address.parts).map(modifiedIndices => address.copy(parts = modifiedIndices))

    override
    def canCompareWithAddress(
      address: TreeAddress,
      that: TreeAddress
    ): Boolean =

      that.isInstanceOf[TreeAddress]

    override
    def isEqualToAddress(
      address: TreeAddress,
      that: TreeAddress
    ): Boolean =

      orderer.compare(address, that) == 0

    override
    def isGreaterThanAddress(
      address: TreeAddress,
      that: TreeAddress
    ): Boolean =

      orderer.compare(address, that) == 1

    override
    def isGreaterOrEqualToAddress(
      address: TreeAddress,
      that: TreeAddress
    ): Boolean =

      orderer.compare(address, that) >= 0

    override
    def isLessThanAddress(
      address: TreeAddress,
      that: TreeAddress
    ): Boolean =

      orderer.compare(address, that) == -1

    override
    def isLessOrEqualToAddress(
      address: TreeAddress,
      that: TreeAddress
    ): Boolean =

      orderer.compare(address, that) <= 0

    override
    def addressToPath(
      address: TreeAddress,
    ): Path =

      pathFactory.create(address.parts.indices.toVector)

    override
    def addressToContent(
      address: TreeAddress,
    ): Content =

      contentFactory.makeContent(address.parts.toVector)
