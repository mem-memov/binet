package net.mem_memov.binet.memory

/**
 * Level corresponds to an index of an address.
 * Elements are organized in levels.
 */
trait Level extends Ordered[Level]:

  protected val number: Int

  def createStore: Store

  def createStock: Stock

  def padBig(content: Address): Either[String, Address]

object Level:

  private lazy val size: Int = UnsignedByte.maximum.toInt + 1

  val top: Level = Level(0)

  def apply(depth: Int): Level = new Level:

    override 
    protected val number: Int = depth

    override 
    def createStore: Store =
      Store(
        Vector.fill[Block](number + 1)(Block())
      )

    override 
    def createStock: Stock =
      val nextLevel = Level(number + 1)
      Stock(
        Vector.fill[Element](Level.size)(Element(nextLevel, Option.empty, Option.empty))
      )

    override 
    def padBig(content: Address): Either[String, Address] =
      content.padBig(number + 1)

    override 
    def compare(that: Level): Int =
      this.number - that.number

    override 
    def equals(that: Any): Boolean =
      that match
        case that: Level => compare(that) == 0
        case _ => false


