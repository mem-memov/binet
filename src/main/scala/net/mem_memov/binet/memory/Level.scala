package net.mem_memov.binet.memory

private[memory] class Level(
  private[Level] val number: Int = 0
) extends Ordered[Level]:

  def createStore: Store =
    new Store(
      new Array[Block](number + 1).map(_ => Block())
    )

  def createStock: Stock =
    val nextLevel = new Level(number + 1)
    Stock(
      new Array[Element](Level.size).map(_ => new Element(nextLevel))
    )

  def padBig(content: Address): Option[Address] =
    content.padBig(number + 1)

  override def compare(that: Level): Int =
    this.number - that.number

  override def equals(that: Any): Boolean =
    that match
      case that: Level => compare(that) == 0
      case _ => false

object Level:

  private lazy val size: Int = UnsignedByte.maximum.toInt + 1