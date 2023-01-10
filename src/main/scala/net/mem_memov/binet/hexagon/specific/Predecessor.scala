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
    general.dot.GetNextDot[Dot, NETWORK],
    general.dot.ToPredecessor[Dot, Predecessor],
    general.dot.ToSuccessor[Dot, SUCCESSOR],
    general.dotReference.IsEmpty[DotReference],
    general.dotReference.R
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
          successors
        else

          predecessor.nextDotReference.(network) match
            case Left(error) => Left(error)
            case Right(nextDotOption) => nextDotOption match
              case None => Right(successors)
              case Some(nextDot) =>
                val predecessor = nextDot.toPredecessor
                if predecessor == firstPredecessor then
                  Right(successors)
                else
                  g(nextDot.toPredecessor, network, firstPredecessor, successors :+ nextDot.toSuccessor)

      g(predecessor, network, predecessor, Vector(predecessor.dot.toSuccessor))