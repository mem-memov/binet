package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.hexagon.general.Position
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

class Factory

object Factory:

  given net_mem_memov_binet_hexagon_specific_Factory_emptyDictionary: general.factory.EmptyDictionary[Factory, Dictionary] with

    import net.mem_memov.binet.memory.Preamble.given

    lazy
    val dictionary: Dictionary =
      Dictionary(Vector(
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory()
      ))

    override def f(): Dictionary = dictionary

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

  given general.factory.MakeVertex[Factory, DotReference, Vertex] with

    override
    def f(
      dotReference: DotReference
    ): Vertex =

      Vertex(dotReference)

  given general.factory.MakeEntry[Factory, Address, Entry] with

    override
    def f(
      position: Position,
      path: Address,
      content: Address
    ): Entry =

      Entry(position, path, content)

  given general.factory.MakeArrow[Factory, Arrow, Entry] with

    override
    def f(
      entries: (Entry, Entry, Entry, Entry, Entry, Entry)
    ): Arrow =

      Arrow(
        DotReference(entries(0)),
        ArrowReference(entries(1)),
        ArrowReference(entries(2)),
        DotReference(entries(3)),
        ArrowReference(entries(4)),
        ArrowReference(entries(5))
      )

  given general.factory.MakeDot[Factory, Dot, Entry] with

    override
    def f(
      entries: (Entry, Entry, Entry, Entry, Entry, Entry)
    ): Dot =

      Dot(
        DotReference(entries(0)),
        DotReference(entries(1)),
        Counter(entries(2)),
        Counter(entries(3)),
        ArrowReference(entries(4)),
        ArrowReference(entries(5))
      )

  given [MEMORY_FACTORY](using
    memory.general.factory.ZeroAddress[MEMORY_FACTORY, Address]
  )(using
    memoryFactory: MEMORY_FACTORY
  ): general.factory.TakeZeroAddress[Factory, Address] with

    override def f(): Address = memoryFactory.zeroAddress()

  given general.factory.MakeArrowDraftBegin[Factory, ArrowDraftBegin, ArrowReference, DotReference] with

    override
    def f(
      sourceDotReference: DotReference,
      previousSourceArrowReference: ArrowReference
    ): ArrowDraftBegin =

      ArrowDraftBegin(
        sourceDotReference,
        previousSourceArrowReference
      )

  given general.factory.MakeArrowDraftEnd[Factory, ArrowDraftEnd, ArrowReference, DotReference] with

    override
    def f(
      sourceDotReference: DotReference,
      previousSourceArrowReference: ArrowReference,
      targetDotReference: DotReference,
      previousTargetArrowReference: ArrowReference
    ): ArrowDraftEnd =

      ArrowDraftEnd(
        sourceDotReference,
        previousSourceArrowReference,
        targetDotReference,
        previousTargetArrowReference
      )
