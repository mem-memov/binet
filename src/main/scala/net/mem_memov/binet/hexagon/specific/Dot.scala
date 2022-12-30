package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Dot(
  identifier: DotReference,
  relationArrowReference: ArrowReference,
  sourceCounter: Counter,
  targetCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetArrowReference: ArrowReference
)

object Dot:

  given [FACTORY, VERTEX](using
    general.factory.MakeVertex[FACTORY, DotReference, VERTEX]
  )(using
    factory: FACTORY
  ): general.dot.ToVertex[Dot, VERTEX] with

    override
    def f(
      dot: Dot
    ): VERTEX =

      factory.makeVertex(dot.identifier)

  given [FACTORY, SOURCE](using
    general.factory.MakeSource[FACTORY, Dot, SOURCE]
  )(using
    factory: FACTORY
  ): general.dot.ToSource[Dot, SOURCE] with

    override
    def f(
      dot: Dot
    ): SOURCE =

      factory.makeSource(dot)

  given [FACTORY, TARGET](using
    general.factory.MakeTarget[FACTORY, Dot, TARGET]
  )(using
    factory: FACTORY
  ): general.dot.ToTarget[Dot, TARGET] with

    override
    def f(
      dot: Dot
    ): TARGET =

      factory.makeTarget(dot)

  given (using
    general.entry.GetAddress1[Entry, Address]
  ): general.dot.IsDot[Dot] with

    override def f(dot: Dot): Boolean =

      dot.address == dot.entry.address1

  given general.dot.GetAddress[Dot, Address] with

    override
    def f(
      dot: Dot
    ): Address =

      dot.address

  given general.dot.GetEntry[Dot, Entry] with

    override
    def f(
      dot: Dot
    ): Entry =

      dot.entry

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address, ARROW]
  ): general.dot.GetParentArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, ARROW] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address1)
      } yield modifiedNetwork

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address, ARROW]
  ): general.dot.GetChildArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, ARROW] =

      network.readArrow(dot.entry.address2)

  given [ARROW, FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, ARROW, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.dot.GetSourceArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      fetcher.fetchArrow(dot.entry.address5, network)

  given [ARROW, FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, ARROW, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.dot.GetTargetArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      fetcher.fetchArrow(dot.entry.address6, network)

  given (using
    general.entry.GetAddress3[Entry, Address]
  ): general.dot.GetSourceCount[Dot, Address] with

    override
    def f(
      dot: Dot
    ): Address =

      dot.entry.getAddress3

  given (using
    general.entry.GetAddress4[Entry, Address]
  ): general.dot.GetTargetCount[Dot, Address] with

    override
    def f(
      dot: Dot
    ): Address =

      dot.entry.getAddress4

  given [NETWORK](using
    general.counter.Increment[Counter, NETWORK]
  ): general.dot.IncrementSourceCount[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, (NETWORK, Dot)] =

      for {
        incrementResult <- dot.sourceCounter.increment(network)
        (modifiedNetwork, modifiedCounter) = incrementResult
      } yield
        val modifiedDot = dot.copy(targetCounter = modifiedCounter)
        (modifiedNetwork, modifiedDot)

  given [NETWORK](using
    general.counter.Increment[Counter, NETWORK]
  ): general.dot.IncrementTargetCount[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, (NETWORK, Dot)] =

      for {
        incrementResult <- dot.targetCounter.increment(network)
        (modifiedNetwork, modifiedCounter) = incrementResult
      } yield
        val modifiedDot = dot.copy(targetCounter = modifiedCounter)
        (modifiedNetwork, modifiedDot)

  given [ARROW_DRAFT_BEGIN, FACTORY](using
    general.factory.MakeArrowDraftBegin[FACTORY, ARROW_DRAFT_BEGIN, ArrowReference, DotIdentifier]
  )(using
    factory: FACTORY
  ): general.dot.BeginArrowDraft[Dot, ARROW_DRAFT_BEGIN] with

    override
    def f(
      dot: Dot
    ): ARROW_DRAFT_BEGIN =

      factory.makeArrowDraftBegin(dot.identifier, dot.sourceArrowReference)

  given (using
    general.counter.IsLarger[Counter]
  ):general.dot.HasMoreSourcesThanTheOtherTargets[Dot] with

    override 
    def f(
      dot: Dot,
      theOther: Dot
    ): Boolean =

      dot.sourceCounter.isLarger(theOther.targetCounter)
      
      
