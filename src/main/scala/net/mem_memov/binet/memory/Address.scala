package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.address.DefaultAddress

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
trait Address extends Ordered[Address] with ShrinkableAddress with CompoundAddress:

  val indices: List[UnsignedByte]

  lazy val length: Int

  def increment: Address

  def decrement: Either[String, Address]

  def isZero: Boolean

  override
  def toString: String

  private[memory]
  def trimBig: Address

  private[memory]
  def padBig(target: Int): Either[String, Address]

object Address:

  val zero: Address = Address(List(UnsignedByte.minimum))

  def apply(parts: List[UnsignedByte]): Address = new DefaultAddress(parts)

trait ShrinkableAddress:

  private[memory]
  def shorten: Option[(UnsignedByte, ShrinkableAddress)]

  private[memory]
  def isEmpty: Boolean

trait CompoundAddress:

  private[memory]
  def hasLength(length: Int): Boolean

  private[memory]
  def zipIndices[A](elements: Vector[A]): Either[String, Vector[(UnsignedByte, A)]]


