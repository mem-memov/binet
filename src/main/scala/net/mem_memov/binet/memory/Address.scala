package net.mem_memov.binet.memory

import zio.*

/**
 * Address has the property that it can be incremented infinitely without overflow.
 * An address consists on indices which are used to retrieve data at different levels of a tree structure.
 */
class Address private (
//  private[Address] val indices: List[UnsignedByte]
  val indices: List[UnsignedByte]
) extends Ordered[Address]:
  
  private[Address]
  lazy val length: Int = indices.length

  def hasLength(length: Int): Boolean =

    this.length == length

  def increment: Task[Address] =

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

    ZIO.succeed(Address(resultIndices))

  def decrement: Task[Address] =

    def minusOne(x: UnsignedByte): (UnsignedByte, Boolean) =
      if x.atMinimum then (UnsignedByte.minimum, true) else (x.decrement, false)

    if length <= 0 then
      ZIO.fail(Exception("Address not decremented: malformed"))
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
        ZIO.fail(Exception("Address not decremented: already at minimum"))
      else
        ZIO.succeed(Address(accumulator.reverse))

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
  def isEmpty: Boolean =

    length == 0

  private[memory]
  def trimBig: Address =
    val trimmedIndices = indices.dropWhile(_.atMinimum)
    val nonEmptyIndices = if trimmedIndices.isEmpty then List(UnsignedByte.minimum) else trimmedIndices
    new Address(nonEmptyIndices)

  private[memory]
  def padBig(target: Int ): Task[Address] =

    if length == target then
      ZIO.succeed(this)
    else
      val trimmed = this.trimBig
      if trimmed.length == target then
        ZIO.succeed(trimmed)
      else
        if trimmed.length > target then
          ZIO.fail(Exception("Address not padded: already too long"))
        else
          val newIndices = List.fill(target - indices.length)(UnsignedByte.minimum) ++ indices
          ZIO.succeed(Address(newIndices))


  private[memory]
  def shorten: Task[Option[(UnsignedByte, Address)]] =

    ZIO.when(length > 0) {
      ZIO.succeed(indices.head -> Address(indices.tail))
    }

//  private[memory]
//  def foreach(f: UnsignedByte => Unit): Unit =
//    indices.foreach(f)
//
//  private[memory]
//  def mapVector[B](f: UnsignedByte => B): Vector[B] =
//    indices.toVector.map(f)

object Address:

  val zero: Address = new Address(List(UnsignedByte.minimum))

  def apply(indices: List[UnsignedByte]): Address =
    new Address(indices)