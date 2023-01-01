package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Dot(
  identifierDotReference: DotReference,
  nextDotReference: DotReference,
  sourceCounter: Counter,
  targetCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetArrowReference: ArrowReference
)

object Dot:

  given (using
    general.dotReference.InDot[DotReference]
  ): general.dot.IsDot[Dot] with

    override
    def f(
      dot: Dot
    ): Boolean =

      dot.identifierDotReference.inDot

  given [FACTORY, VERTEX](using
    general.factory.MakeVertex[FACTORY, DotReference, VERTEX]
  )(using
    factory: FACTORY
  ): general.dot.ToVertex[Dot, VERTEX] with

    override
    def f(
      dot: Dot
    ): VERTEX =

      factory.makeVertex(dot.identifierDotReference)

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

  given [ARROW, FETCHER, NETWORK](using
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference]
  ): general.dot.GetSourceArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      network.readArrow(dot.sourceArrowReference)

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference]
  ): general.dot.GetTargetArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[ARROW]] =


      network.readArrow(dot.targetArrowReference)

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
    general.factory.MakeArrowDraftBegin[FACTORY, ARROW_DRAFT_BEGIN, ArrowReference, DotReference]
  )(using
    factory: FACTORY
  ): general.dot.BeginArrowDraft[Dot, ARROW_DRAFT_BEGIN] with

    override
    def f(
      dot: Dot
    ): ARROW_DRAFT_BEGIN =

      factory.makeArrowDraftBegin(dot.identifierDotReference, dot.sourceArrowReference)

  given (using
    general.counter.IsLarger[Counter]
  ):general.dot.HasMoreSourcesThanTheOtherTargets[Dot] with

    override
    def f(
      dot: Dot,
      theOther: Dot
    ): Boolean =

      dot.sourceCounter.isLarger(theOther.targetCounter)

  given (using
    general.dotReference.InSameDirection[DotReference]
  ): general.dot.IsReferencedBy[Dot, DotReference] with

    override
    def f(
      dot: Dot,
      dotReference: DotReference
    ): Boolean =

      dot.identifierDotReference.inSameDirection(dotReference)

  given [NETWORK](using
    general.dot.SetNextDot[Dot, DotReference, NETWORK]
  ): general.dot.GiveIdentifierToPredecessor[Dot, NETWORK] with

    override def f(
      dot: Dot,
      predecessorDot: Dot,
      network: NETWORK
    ): Either[String, (NETWORK, Dot)] =

      predecessorDot.setNextDot(dot.identifierDotReference, network)

  given [NETWORK](using
    general.dotReference.ReferencePath[DotReference, NETWORK]
  ): general.dot.SetNextDot[Dot, DotReference, NETWORK] with

    override
    def f(
      dot: Dot,
      identifierDotReference: DotReference,
      network: NETWORK
    ): Either[String, (NETWORK, Dot)] =

      for {
        result <- dot.nextDotReference.referencePath(identifierDotReference, network)
        (modifiedNetwork, modifiedDotReference) = result
      } yield
        val modifiedDot = dot.copy(nextDotReference = modifiedDotReference)
        (modifiedNetwork, modifiedDot)