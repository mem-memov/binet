package net.mem_memov.binet.memory

class Address(
  private[Address] val indices: List[UnsignedByte]
) extends Ordered[Address]:
  
  private[Address] lazy val length: Int = indices.length

  def hasLength(length: Int): Boolean =
    this.length == length

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

    new Address(resultIndices)
  
  def isEmpty: Boolean =
    length == 0

  override def compare(that: Address): Int =
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

  override def equals(that: Any): Boolean =
    that match
      case that: Address => compare(that) == 0
      case _ => false

  override def toString: String =
    indices.map(_.toInt.toString()).mkString("Address(", ",", ")")

  private[memory] def trimBig: Address =
    val trimmedIndices = indices.dropWhile(_.atMinimum)
    val nonEmptyIndices = if trimmedIndices.isEmpty then List(UnsignedByte.minimum) else trimmedIndices
    new Address(nonEmptyIndices)

  private[memory] def padBig(target: Int): Option[Address] =
    if length == target then
      Some(this)
    else
      val trimmed = this.trimBig
      if trimmed.length == target then
        Some(trimmed)
      else
        if trimmed.length > target then
          None
        else
          Some(
            new Address(
              List.fill(target - indices.length)(UnsignedByte.minimum) ++ indices
            )
          )

  private[memory] def shorten: Option[(UnsignedByte, Address)] =
    if length == 0 then
      None
    else
      Some(
        indices.head -> new Address(indices.tail)
      )
  
  private[memory] def foreach(f: UnsignedByte => Unit): Unit =
    indices.foreach(f)