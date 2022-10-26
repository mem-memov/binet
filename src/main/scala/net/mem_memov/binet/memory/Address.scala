package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.address.DefaultAddress

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
trait Address:

  lazy val indices: List[UnsignedByte]

  lazy val length: Int

  def increment: Address

  def decrement: Either[String, Address]

  def isZero: Boolean

  override
  def toString: String

  private[memory]
  def isEmpty: Boolean

  private[memory]
  def trimBig: Address

  private[memory]
  def padBig(target: Int ): Either[String, Address]

  private[memory]
  def shorten: Option[(UnsignedByte, Address)]

  def expandStore(store: Store): Store

  def zipIndices(elements: Vector[Block]): Either[String, Vector[(UnsignedByte, Block)]]

  def canCompare(that: Address): Boolean
  
  def isEqual(that: Address): Boolean

  def isGreater(that: Address): Boolean

  def isGreaterOrEqual(that: Address): Boolean



