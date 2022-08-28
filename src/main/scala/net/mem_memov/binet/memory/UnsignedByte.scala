package net.mem_memov.binet.memory

import scala.annotation.targetName

case class UnsignedByte(value: Byte) extends AnyVal:

  inline def atMinimum: Boolean = value == Byte.MinValue

  inline def atMaximum: Boolean = value == Byte.MaxValue

  inline def toInt: Int = value.toInt + Byte.MaxValue.toInt + 1

  inline def increment: UnsignedByte = UnsignedByte((value + 1).toByte)
  
  inline def decrement: UnsignedByte = UnsignedByte((value - 1).toByte)
  
  @targetName("nonEqual")
  inline def !=(that: UnsignedByte): Boolean = this.value != that.value

  @targetName("isEqual")
  inline def ==(that: UnsignedByte): Boolean = this.value == that.value

  @targetName("lessThan")
  inline def <(that: UnsignedByte): Boolean = this.value < that.value

  @targetName("lessThanOrEqual")
  inline def <=(that: UnsignedByte): Boolean = this.value <= that.value

  @targetName("greaterThan")
  inline def >(that: UnsignedByte): Boolean = this.value > that.value

  @targetName("greaterThanOrEqual")
  inline def >=(that: UnsignedByte): Boolean = this.value >= that.value

object UnsignedByte:

  def minimum: UnsignedByte = UnsignedByte(Byte.MinValue)

  def maximum: UnsignedByte = UnsignedByte(Byte.MaxValue)
  
  def fromInt(value: Int): UnsignedByte = UnsignedByte((value - Byte.MaxValue - 1).toByte)
