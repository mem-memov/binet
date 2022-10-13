package net.mem_memov.binet.memory

/**
 * Level corresponds to an index of an address.
 * Elements are organized in levels.
 */
private[memory] class Level private (
  private[Level] val number: Int = 0
) extends Ordered[Level]:

  private val nextLevel: Option[Level] = None

  def createStore: Store =
    new Store(
      Vector.fill[Block](number + 1)(Block())
    )

  def createStock: Stock =
    Stock(
      Vector.fill[Element](Level.size)(
        Element(
          nextLevel.getOrElse(new Level(number + 1)),
          Option.empty,
          Option.empty
        )
      )
    )

  def padBig(content: Address): Either[String, Address] =
    content.padBig(number + 1)

  override def compare(that: Level): Int =
    this.number - that.number

  override def equals(that: Any): Boolean =
    that match
      case that: Level => compare(that) == 0
      case _ => false

object Level:

  private lazy val size: Int = UnsignedByte.maximum.toInt + 1

  val top: Level = new Level

