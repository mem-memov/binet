package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeAddress.*
import net.mem_memov.binet.memory.tree.treeFactory.{AddressFactory, ContentFactory, PathFactory}

case class TreeAddress(
  parts: List[UnsignedByte],
  formatter: Formatter,
  orderer: Orderer, 
  resizer: Resizer
)(using
  addressFactory: AddressFactory,
  contentFactory: ContentFactory,
  pathFactory: PathFactory
) extends Address:

  override
  lazy val indices: List[UnsignedByte] = parts

  override
  lazy val length: Int = indices.length

  override
  def increment: TreeAddress =

    this.copy(parts = resizer.increment(indices))

  override
  def decrement: Either[String, TreeAddress] =
    
    resizer.decrement(indices).map(indices => this.copy(parts = indices))

  override
  def isZero: Boolean =

    length == 1 && indices.head == UnsignedByte.minimum

  override
  def toString: String =

    indices.map(_.toInt.toString()).mkString("Address(", ",", ")")

  private[memory]
  override
  def isEmpty: Boolean =

    length == 0

  private[memory]
  override
  def trimBig: TreeAddress =

    this.copy(parts = formatter.trimBig(indices))

  private[memory]
  override
  def padBig(target: Int): Either[String, TreeAddress] =

    formatter.padBig(target, indices).map(modifiedIndices => this.copy(parts = modifiedIndices))

  override
  def canCompare(
    that: Address
  ): Boolean =
    
    that.isInstanceOf[TreeAddress]
  
  override
  def isEqual(
    that: Address
  ): Boolean =
    
    orderer.compare(this, that) == 0
  
  override
  def isGreater(
    that: Address
  ): Boolean =
    
    orderer.compare(this, that) == 1

  override
  def isGreaterOrEqual(
    that: Address
  ): Boolean =
    
    orderer.compare(this, that) >= 0

  override
  def isLess(
    that: Address
  ): Boolean =

    orderer.compare(this, that) == -1

  override
  def isLessOrEqual(
    that: Address
  ): Boolean =

    orderer.compare(this, that) <= 0

  override
  def toPath: Path =

    pathFactory.create(indices.toVector)

  override
  def toContent: Content =

    contentFactory.makeContent(indices.toVector)