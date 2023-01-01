package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Predecessor(
  dot: Dot
)

object Predecessor:

  given [NETWORK](using
    general.dot.GiveIdentifierToPredecessor[Dot, NETWORK]
  ): general.predecessor.Precede[Predecessor, Dot, NETWORK] with

    override
    def f(
      predecessor: Predecessor,
      successorDot: Dot,
      network: NETWORK
    ): Either[String, (NETWORK, Predecessor)] =

      for {
        result <- successorDot.giveIdentifierToPredecessor(predecessor.dot, network)
        (modifiedNetwork, modifiedDot) = result
      } yield
        val modifiedPredecessor = predecessor.copy(dot = modifiedDot)
        (modifiedNetwork, modifiedPredecessor)

  given [NETWORK, SUCCESSOR](using
    general.dot.GetNextDot[Dot, NETWORK],
    general.dot.ToPredecessor[Dot, Predecessor],
    general.dot.ToSuccessor[Dot, SUCCESSOR]
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

        predecessor.dot.getNextDot(network) match
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