package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeAddress.*
import net.mem_memov.binet.memory.tree.treeFactory.{AddressFactory, ContentFactory, PathFactory}

case class TreeAddress(
  parts: List[UnsignedByte],
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

    val trimmedIndices = indices.dropWhile(_.atMinimum)
    val nonEmptyIndices = if trimmedIndices.isEmpty then List(UnsignedByte.minimum) else trimmedIndices
    this.copy(parts = nonEmptyIndices)

  private[memory]
  override
  def padBig(target: Int): Either[String, TreeAddress] =

    if length == target then
      Right(this)
    else
      val trimmed = this.trimBig
      if trimmed.length == target then
        Right(trimmed)
      else
        if trimmed.length > target then
          Left("Address not padded: already too long")
        else
          val newIndices = List.fill(target - indices.length)(UnsignedByte.minimum) ++ indices
          Right(this.copy(parts = newIndices))

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