package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

class Factory

object Factory:

  given net_mem_memov_binet_hexagon_specific_Factory_emptyDictionary: general.factory.EmptyDictionary[Factory, Dictionary] with

    import net.mem_memov.binet.memory.Preamble.given

    lazy
    val dictionary: Dictionary =
      Dictionary(
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory()
      )

    override def f(): Dictionary = dictionary

  given general.factory.EmptyEntry[Factory, Entry] with

    import net.mem_memov.binet.memory.Preamble.given

    lazy
    val zero = factory.zeroAddress()

    lazy
    val entry = Entry(zero, zero, zero, zero, zero, zero)

    override def f(): Entry = entry

  given general.factory.EmptyNetwork[Factory, Network] with

    lazy
    val network = Network(net_mem_memov_binet_hexagon_specific_Factory_emptyDictionary.f())

    override def f(): Network = network

  given general.factory.MakeSource[Factory, Dot, Source] with

    override
    def f(
      dot: Dot
    ): Source =

      Source(dot)

  given general.factory.MakeTarget[Factory, Dot, Target] with

    override
    def f(
      dot: Dot
    ): Target =

      Target(dot)

  given general.factory.MakeVertex[Factory, Dot, Vertex] with

    override
    def f(
      dot: Dot
    ): Vertex =

      Vertex(dot)

  given general.factory.MakeEntry[Factory, Address, Entry] with

    override
    def f(
      address1: Address,
      address2: Address,
      address3: Address,
      address4: Address,
      address5: Address,
      address6: Address
    ): Entry =

      Entry(
        address1,
        address2,
        address3,
        address4,
        address5,
        address6
      )
