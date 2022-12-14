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

  given general.factory.MakePredecessor[Factory, ArrowReference, Counter, DotReference, Predecessor] with

    override
    def f(
      dotReference: DotReference,
      nextDotReference: DotReference,
      sourceCounter: Counter,
      targetCounter: Counter,
      sourceArrowReference: ArrowReference,
      targetArrowReference: ArrowReference
    ): Predecessor =

      Predecessor(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )

  given general.factory.MakeSuccessor[Factory, ArrowReference, Counter, DotReference, Successor] with

    override
    def f(
      dotReference: DotReference,
      nextDotReference: DotReference,
      sourceCounter: Counter,
      targetCounter: Counter,
      sourceArrowReference: ArrowReference,
      targetArrowReference: ArrowReference
    ): Successor =

      Successor(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )

  given general.factory.MakeSource[Factory, ArrowReference, Counter, DotReference, Source] with

    override
    def f(
      dotReference: DotReference,
      nextDotReference: DotReference,
      sourceCounter: Counter,
      targetCounter: Counter,
      sourceArrowReference: ArrowReference,
      targetArrowReference: ArrowReference
    ): Source =

      Source(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )

  given general.factory.MakeTarget[Factory, ArrowReference, Counter, DotReference, Target] with

    override
    def f(
      dotReference: DotReference,
      nextDotReference: DotReference,
      sourceCounter: Counter,
      targetCounter: Counter,
      sourceArrowReference: ArrowReference,
      targetArrowReference: ArrowReference
    ): Target =

      Target(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )

  given general.factory.MakeHead[Factory, ArrowReference, DotReference, Head] with

    override
    def f(
      tailDotReference: DotReference,
      previousTailArrowReference: ArrowReference,
      nextTailArrowReference: ArrowReference,
      headDotReference: DotReference,
      previousHeadArrowReference: ArrowReference,
      nextHeadArrowReference: ArrowReference
    ): Head =

      Head(
        tailDotReference,
        previousTailArrowReference,
        nextTailArrowReference,
        headDotReference,
        previousHeadArrowReference,
        nextHeadArrowReference
      )

  given general.factory.MakeTail[Factory, ArrowReference, DotReference, Tail] with

    override
    def f(
      tailDotReference: DotReference,
      previousTailArrowReference: ArrowReference,
      nextTailArrowReference: ArrowReference,
      headDotReference: DotReference,
      previousHeadArrowReference: ArrowReference,
      nextHeadArrowReference: ArrowReference
    ): Tail =

      Tail(
        tailDotReference,
        previousTailArrowReference,
        nextTailArrowReference,
        headDotReference,
        previousHeadArrowReference,
        nextHeadArrowReference
      )