package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.address.DefaultAddress

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
trait Address extends
  Ordered[Address]
  with IndicesProvidingAddress
  with LengthProvidingAddress
  with IncrementingAddress
  with DecrementingAddress
  with ZeroCheckingAddress
  with SerializableAddress
  with EmptyCheckingAddress
  with TrimmedAddress
  with PaddedAddress
  with ShrinkableAddress
  with ZippingAddress
  with StoreExpandingAddress

trait IndicesProvidingAddress:

  lazy val indices: List[UnsignedByte]

trait LengthProvidingAddress:

  lazy val length: Int

trait IncrementingAddress:

  def increment: Address

trait DecrementingAddress:

  def decrement: Either[String, Address]

trait ZeroCheckingAddress:

  def isZero: Boolean

trait SerializableAddress:

  override
  def toString: String

trait EmptyCheckingAddress:

  private[memory]
  def isEmpty: Boolean

trait TrimmedAddress:

  private[memory]
  def trimBig: Address

trait PaddedAddress:

  private[memory]
  def padBig(target: Int ): Either[String, Address]

trait ShrinkableAddress:

  private[memory]
  def shorten: Option[(UnsignedByte, Address)]

trait StoreExpandingAddress:

  def expandStore(store: Store): Store

trait ZippingAddress:

  def zipIndices(elements: Vector[WritableBlock]): Either[String, Vector[(UnsignedByte, WritableBlock)]]



