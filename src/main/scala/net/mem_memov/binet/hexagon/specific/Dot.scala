package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Dot(
  identifierDotReference: DotReference,
  nextDotReference: DotReference,
  source: Source,
  target: Target
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

  given general.dot.ToSource[Dot, Source] with

    override
    def f(
      dot: Dot
    ): Source =

      dot.source

  given general.dot.ToTarget[Dot, Target] with

    override
    def f(
      dot: Dot
    ): Target =

      dot.target

  given [FACTORY, PREDECESSOR](using
    general.factory.MakePredecessor[FACTORY, Dot, PREDECESSOR]
  )(using
    factory: FACTORY
  ): general.dot.ToPredecessor[Dot, PREDECESSOR] with

    override
    def f(
      dot: Dot
    ): PREDECESSOR =

      factory.makePredecessor(dot)

  given [FACTORY, SUCCESSOR](using
    general.factory.MakeSuccessor[FACTORY, Dot, SUCCESSOR]
  )(using
    factory: FACTORY
  ): general.dot.ToSuccessor[Dot, SUCCESSOR] with

    override
    def f(
      dot: Dot
    ): SUCCESSOR =

      factory.makeSuccessor(dot)

  given [NETWORK](using
    general.network.ReadDot[NETWORK, Dot, DotReference],
    general.dotReference.IsEmpty[DotReference]
  ): general.dot.GetNextDot[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[Dot]] =

      if dot.nextDotReference.isEmpty then
        Right(None)
      else
        for {
          nextDot <- network.readDot(dot.nextDotReference)
        } yield Some(nextDot)

  given [ARROW, NETWORK](using
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