package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Dot(
  dotReference: DotReference,
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

      dot.dotReference.inDot

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
    general.factory.MakeSource[FACTORY, ARROW_REFERENCE, DOT_REFERENCE, SOURCE]
  )(using
    factory: FACTORY
  ): general.dot.ToSource[Dot, SOURCE] with

    override
    def f(
      dot: Dot
    ): SOURCE =

      factory.makeSource(
        dot.dotReference,
        dot.nextDotReference,
        dot.sourceCounter,
        dot.targetCounter,
        dot.sourceArrowReference,
        dot.targetArrowReference
      )

  given [FACTORY, TARGET](using
    general.factory.MakeTargete[FACTORY, ARROW_REFERENCE, DOT_REFERENCE, TARGET]
  )(using
    factory: FACTORY
  ): general.dot.ToTarget[Dot, TARGET] with

    override
    def f(
      dot: Dot
    ): TARGET =

      factory.makeTarget(
        dot.dotReference,
        dot.nextDotReference,
        dot.sourceCounter,
        dot.targetCounter,
        dot.sourceArrowReference,
        dot.targetArrowReference
      )

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