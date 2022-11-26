package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

class Factory

object Factory:

  lazy
  val length: Int = general.UnsignedByte.maximum.toInt + 1

  lazy
  val minimum: general.UnsignedByte = general.UnsignedByte.minimum

  given zeroAddress: general.factory.ZeroAddress[Factory, Address] with

    lazy
    val address: Address = Address(List(minimum))

    override
    def f(): Address = address

  given makeAddress: general.factory.MakeAddress[Factory, Address] with

    override
    def f(indices: List[general.UnsignedByte]): Address = Address(indices)

  given emptyBlock: general.factory.EmptyBlock[Factory, Block] with

    lazy
    val block: Block = Block(Vector.fill(length)(minimum))

    override
    def f(): Block = block

  given emptyElement: general.factory.EmptyElement[Factory, Element] with

    lazy
    val element: Element = Element(None, None)

    override
    def f(): Element = element

  given emptyStock: general.factory.EmptyStock[Factory, Stock] with

    lazy
    val stock: Stock = Stock(Vector.fill(length)(emptyElement.f()))

    override
    def f(): Stock = stock

  given emptyStore: general.factory.EmptyStore[Factory, Store] with

    lazy
    val store: Store = Store(Vector(emptyBlock.f()))

    override
    def f(): Store = store

  given emptyInventory: general.factory.EmptyInventory[Factory, Inventory] with

    lazy
    val inventory: Inventory = Inventory(
      zeroAddress.f(),
      emptyElement.f()
    )

    override
    def f(): Inventory = inventory




