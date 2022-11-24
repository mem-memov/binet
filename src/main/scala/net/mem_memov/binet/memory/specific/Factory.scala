package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

class Factory

object Factory:

  lazy
  val length: Int = general.UnsignedByte.maximum.toInt + 1

  lazy
  val minimum: general.UnsignedByte = general.UnsignedByte.minimum

  given net_mem_memov_binet_memory_specific_Factory_ZeroAddress: general.factory.ZeroAddress[Factory, Address] with

    lazy
    val address: Address = Address(List(minimum))

    override
    def f(): Address = address

  given net_mem_memov_binet_memory_specific_Factory_MakeAddress: general.factory.MakeAddress[Factory, Address] with

    override
    def f(indices: List[general.UnsignedByte]): Address = Address(indices)

  given net_mem_memov_binet_memory_specific_Factory_EmptyBlock: general.factory.EmptyBlock[Factory, Block] with

    lazy
    val block: Block = Block(Vector.fill(length)(minimum))

    override
    def f(): Block = block

  given net_mem_memov_binet_memory_specific_Factory_EmptyElement: general.factory.EmptyElement[Factory, Element] with

    lazy
    val element: Element = Element(None, None)

    override
    def f(): Element = element

  given net_mem_memov_binet_memory_specific_Factory_EmptyStock: general.factory.EmptyStock[Factory, Stock] with

    lazy
    val stock: Stock = Stock(Vector.fill(length)(net_mem_memov_binet_memory_specific_Factory_EmptyElement.f()))

    override
    def f(): Stock = stock

  given net_mem_memov_binet_memory_specific_Factory_EmptyStore: general.factory.EmptyStore[Factory, Store] with

    lazy
    val store: Store = Store(Vector(net_mem_memov_binet_memory_specific_Factory_EmptyBlock.f()))

    override
    def f(): Store = store

  given net_mem_memov_binet_memory_specific_Factory_EmptyInventory: general.factory.EmptyInventory[Factory, Inventory] with

    lazy
    val inventory: Inventory = Inventory(
      net_mem_memov_binet_memory_specific_Factory_ZeroAddress.f(),
      net_mem_memov_binet_memory_specific_Factory_EmptyElement.f()
    )

    override
    def f(): Inventory = inventory




