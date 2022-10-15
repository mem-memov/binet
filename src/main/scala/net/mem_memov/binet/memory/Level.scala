package net.mem_memov.binet.memory

/**
 * Level corresponds to an index of an address.
 * Elements are organized in levels.
 */
trait Level:

  def createStore(): Store

  def createStock(): Stock

  def padBig(content: Address): Either[String, Address]

object Level:

  private lazy val size: Int = UnsignedByte.maximum.toInt + 1

  val top: Level = Level(0)

  def apply(number: Int): Implementation = new Implementation(number)

  class Implementation(number: Int) extends Level:

    override
    def createStore(): Store =
      Store(
        Vector.fill[Block](number + 1)(Block())
      )

    override
    def createStock(): Stock =
      val nextLevel = new Implementation(number + 1)
      Stock(
        Vector.fill[Element](Level.size)(Element(nextLevel, Option.empty, Option.empty))
      )

    override
    def padBig(content: Address): Either[String, Address] =
      content.padBig(number + 1)

