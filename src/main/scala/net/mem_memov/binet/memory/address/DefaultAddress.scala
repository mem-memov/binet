package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory._

case class DefaultAddress(indices: List[UnsignedByte]) extends Address:

  override
  lazy val length: Int = indices.length

  override
  def increment: Address =

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

    this.copy(indices = resultIndices)

  override
  def decrement: Either[String, Address] =

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
        Right(this.copy(indices = accumulator.reverse))

  override
  def isZero: Boolean =

    length == 1 && indices.head == UnsignedByte.minimum

  override
  def compare(that: Address): Int =

    val trimmedThis = this.trimBig
    val trimmedThat = that.trimBig
    if trimmedThis.length != trimmedThat.length then
      trimmedThis.length - trimmedThat.length
    else
      val difference = trimmedThis.indices.zip(trimmedThat.indices)
        .dropWhile { case (thisIndex, thatIndex) =>
          thisIndex == thatIndex
        }
      if difference.isEmpty then
        0
      else
        val (thisIndex, thatIndex) = difference(0)
        if thisIndex > thatIndex then 1 else -1

  override
  def equals(that: Any): Boolean =

    that match
      case that: Address => compare(that) == 0
      case _ => false

  override
  def toString: String =

    indices.map(_.toInt.toString()).mkString("Address(", ",", ")")

  private[memory]
  override
  def isEmpty: Boolean =

    length == 0

  private[memory]
  override
  def trimBig: Address =
    val trimmedIndices = indices.dropWhile(_.atMinimum)
    val nonEmptyIndices = if trimmedIndices.isEmpty then List(UnsignedByte.minimum) else trimmedIndices
    this.copy(indices = nonEmptyIndices)

  private[memory]
  override
  def padBig(target: Int ): Either[String, Address] =

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
          Right(this.copy(indices = newIndices))

  private[memory]
  override
  def shorten: Option[(UnsignedByte, Address)] =

    if length > 0 then
      Some(indices.head -> this.copy(indices = indices.tail))
    else
      None

  override
  def zipIndices(elements: Vector[WritableBlock]): Either[String, Vector[(UnsignedByte, WritableBlock)]] =

    if elements.length != indices.length then
      Left("Lengths differ")
    else
      Right(indices.toVector.zip(elements))

  override
  def expandStore(store: Store): Store =

    store.expand(length)

object DefaultAddress:

  val zero: Address = DefaultAddress(List(UnsignedByte.minimum))


