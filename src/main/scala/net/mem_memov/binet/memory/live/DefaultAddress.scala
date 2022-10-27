package net.mem_memov.binet.memory.live

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.address.defaultAddress.*

case class DefaultAddress(parts: List[UnsignedByte], ordering: Ordering) extends Address:

  override
  lazy val indices: List[UnsignedByte] = parts

  override
  lazy val length: Int = indices.length

  override
  def increment: DefaultAddress =

    def plusOne(x: UnsignedByte): (UnsignedByte, Boolean) = if x.atMaximum then (UnsignedByte.minimum, true) else (x.increment, false)

    val (accumulator, _, hasOverflow) = indices.reverse.foldLeft((List.empty[UnsignedByte], true, false)) {
      case ((accumulator, isStart, hasOverflow), index) =>
        if isStart then
          val (incrementedIndex, overflow) = plusOne(index)
          (incrementedIndex :: accumulator, false, overflow)
        else
          if hasOverflow then
            val (incrementedIndex, overflow) = plusOne(index)
            (incrementedIndex :: accumulator, false, overflow)
          else
            (index :: accumulator, false, false)
    }

    val resultIndices = if hasOverflow then UnsignedByte.minimum.increment :: accumulator.reverse else accumulator.reverse

    this.copy(parts = resultIndices)

  override
  def decrement: Either[String, DefaultAddress] =

    def minusOne(x: UnsignedByte): (UnsignedByte, Boolean) =
      if x.atMinimum then (UnsignedByte.minimum, true) else (x.decrement, false)

    if length <= 0 then
      Left("Address not decremented: malformed")
    else
      val (accumulator, _, hasOverflow) = indices.reverse.foldLeft((List.empty[UnsignedByte], true, false)) {
        case ((accumulator, isStart, hasOverflow), index) =>
          if isStart then
            val (decrementedIndex, overflow) = minusOne(index)
            (decrementedIndex :: accumulator, false, overflow)
          else
            if hasOverflow then
              val (decrementedIndex, overflow) = minusOne(index)
              (decrementedIndex :: accumulator, false, overflow)
            else
              (index :: accumulator, false, false)
      }

      if hasOverflow then
        Left("Address not decremented: already at minimum")
      else
        Right(this.copy(parts = accumulator.reverse))

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
  def trimBig: DefaultAddress =
    
    val trimmedIndices = indices.dropWhile(_.atMinimum)
    val nonEmptyIndices = if trimmedIndices.isEmpty then List(UnsignedByte.minimum) else trimmedIndices
    this.copy(parts = nonEmptyIndices)

  private[memory]
  override
  def padBig(target: Int): Either[String, DefaultAddress] =

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

  private[memory]
  override
  def shorten: Option[(UnsignedByte, DefaultAddress)] =

    if length > 0 then
      Some(indices.head -> this.copy(parts = indices.tail))
    else
      None

  override
  def zipIndices(elements: Vector[Block]): Either[String, Vector[(UnsignedByte, Block)]] =

    if elements.length != indices.length then
      Left("Lengths differ")
    else
      Right(indices.toVector.zip(elements))

  override
  def expandStore(store: Store): Store =

    store.expand(length)
    
  override
  def canCompare(that: Address): Boolean =
    that.isInstanceOf[DefaultAddress]
  
  override
  def isEqual(that: Address): Boolean =
    ordering.compare(this, that) == 0
  
  override
  def isGreater(that: Address): Boolean =
    ordering.compare(this, that) == 1

  override
  def isGreaterOrEqual(that: Address): Boolean =
    ordering.compare(this, that) >= 0

  override
  def isLess(that: Address): Boolean =

    ordering.compare(this, that) == -1

  override
  def isLessOrEqual(that: Address): Boolean =

    ordering.compare(this, that) <= 0