package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Predecessor(
  dotReference: DotReference,
  nextDotReference: DotReference,
  sourceCounter: Counter,
  targetCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetArrowReference: ArrowReference
)

object Predecessor:

  given [NETWORK](using
    general.dotReference.ReferencePath[DotReference, NETWORK]
  ): general.predecessor.Precede[Predecessor, DotReference, NETWORK] with

    override
    def f(
      predecessor: Predecessor,
      successorDotReference: DotReference,
      network: NETWORK
    ): Either[String, (NETWORK, Predecessor)] =

      for {
        result <- predecessor.nextDotReference.referencePath(successorDotReference, network)
        (modifiedNetwork, modifiedDotReference) = result
      } yield
        val modifiedPredecessor = predecessor.copy(nextDotReference = modifiedDotReference)
        (modifiedNetwork, modifiedPredecessor)

  given [NETWORK, SUCCESSOR](using
    general.successor.ToPredecessor[SUCCESSOR, Predecessor],
    general.predecessor.ToSuccessor[Predecessor, SUCCESSOR],
    general.dotReference.IsEmpty[DotReference],
    general.dotReference.ReadSuccessor[DotReference, NETWORK, SUCCESSOR]
  ): general.predecessor.ReadSuccessors[Predecessor, NETWORK, SUCCESSOR] with

    override def f(
      predecessor: Predecessor,
      network: NETWORK
    ): Either[String, Vector[SUCCESSOR]] =

      @tailrec
      def g(
        predecessor: Predecessor,
        network: NETWORK,
        firstPredecessor: Predecessor,
        successors: Vector[SUCCESSOR]
      ): Either[String, Vector[SUCCESSOR]] =

        if predecessor.nextDotReference.isEmpty then
          Right(successors)
        else
          predecessor.nextDotReference.readSuccessor(network) match
            case Left(error) => Left(error)
            case Right(successorOption) => successorOption match
              case None => Right(successors)
              case Some(successor) =>
                val predecessor = successor.toPredecessor
                if predecessor == firstPredecessor then
                  Right(successors)
                else
                  g(predecessor, network, firstPredecessor, successors :+ successor)

      g(predecessor, network, predecessor, Vector(predecessor.toSuccessor))
      
  given [FACTORY, SUCCESSOR](using
    general.factory.MakeSuccessor[FACTORY, ArrowReference, Counter, DotReference, SUCCESSOR]
  )(using
    factory: FACTORY
  ): general.predecessor.ToSuccessor[Predecessor, SUCCESSOR] with

    override 
    def f(
      predecessor: Predecessor
    ): SUCCESSOR =

      factory.makeSuccessor(
        predecessor.dotReference,
        predecessor.nextDotReference,
        predecessor.sourceCounter,
        predecessor.targetCounter,
        predecessor.sourceArrowReference,
        predecessor.targetArrowReference
      )