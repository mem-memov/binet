package net.mem_memov.binet.memory

import scala.annotation.targetName

case class UnsignedByte(value: Byte) extends AnyVal:

  inline def atMinimum: Boolean = value == Byte.MinValue

  inline def atMaximum: Boolean = value == Byte.MaxValue

  inline def toIndex: Int = value.toInt

  inline def increment: UnsignedByte = UnsignedByte((value + 1).toByte)
  
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