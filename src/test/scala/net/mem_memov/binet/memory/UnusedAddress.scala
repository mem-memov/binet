package net.mem_memov.binet.memory

trait UnusedAddress(fail: String => Nothing) extends Address:

  override
  lazy val indices: List[UnsignedByte] =

    fail("unexpected")

  override
  lazy val length: Int =

    fail("unexpected")

  override
  def increment: Address =

    fail("unexpected")

  override
  def decrement: Either[String, Address] =

    fail("unexpected")

  override
  def isZero: Boolean =

    fail("unexpected")

  override
  def toString: String =

    fail("unexpected")

  private[memory]
  override
  def isEmpty: Boolean =

    fail("unexpected")

  private[memory]
  override
  def trimBig: Address =

    fail("unexpected")

  private[memory]
  override
  def padBig(target: Int ): Either[String, Address] =

    fail("unexpected")

  private[memory]
  override
  def shorten: Option[(UnsignedByte, Address)] =

    fail("unexpected")

  override
  def canCompare(that: Address): Boolean =

    fail("unexpected")

  override
  def isEqual(that: Address): Boolean =

    fail("unexpected")

  override
  def isGreater(that: Address): Boolean =

    fail("unexpected")

  override
  def isGreaterOrEqual(that: Address): Boolean =

    fail("unexpected")

  override
  def isLess(that: Address): Boolean =

    fail("unexpected")

  override
  def isLessOrEqual(that: Address): Boolean =

    fail("unexpected")

  override
  def toPath: Path =

    fail("unexpected")

  override
  def toContent: Content =

    fail("unexpected")