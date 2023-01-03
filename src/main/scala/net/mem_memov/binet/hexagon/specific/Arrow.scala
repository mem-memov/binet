package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general.dotReference.InArrow
import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  tail: Tail,
  head: Head
)

object Arrow:

  given (using
    general.dotReference.InArrow[DotReference]
  ): general.arrow.IsArrow[Arrow] with

    override def f(arrow: Arrow): Boolean =

      arrow.sourceDotReference.inArrow

  given [DOT, NETWORK](using
    general.network.ReadDot[NETWORK, DOT, DotReference]
  ): general.arrow.GetSourceDot[Arrow, DOT, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, DOT] =

      network.readDot(arrow.sourceDotReference)

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference]
  ): general.arrow.GetPreviousSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      network.readArrow(arrow.previousSourceArrowReference)

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference]
  ): general.arrow.GetNextSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      network.readArrow(arrow.nextSourceArrowReference)

  given [DOT, NETWORK](using
    general.network.ReadDot[NETWORK, DOT, DotReference]
  ): general.arrow.GetTargetDot[Arrow, DOT, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, DOT] =

      network.readDot(arrow.targetDotReference)

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference]
  ): general.arrow.GetPreviousTargetArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      network.readArrow(arrow.previousTargetArrowReference)

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference]
  ): general.arrow.GetNextTargetArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      network.readArrow(arrow.nextTargetArrowReference)

  given [DOT](using
    general.dot.IsReferencedBy[DOT, DotReference]
  ):general.arrow.HasSourceDot[Arrow, DOT] with

    override
    def f(
      arrow: Arrow,
      sourceDot: DOT
    ): Boolean =

      sourceDot.isReferencedBy(arrow.sourceDotReference)

  given [DOT](using
    general.dot.IsReferencedBy[DOT, DotReference]
  ): general.arrow.HasTargetDot[Arrow, DOT] with

    override
    def f(
      arrow: Arrow,
      targetDot: DOT
    ): Boolean =

      targetDot.isReferencedBy(arrow.targetDotReference)

  given [NETWORK](using
    general.arrow.SetReference[Arrow, ArrowReference, NETWORK]
  ): general.arrow.SetNextSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      nextSourceArrow: Arrow,
      network: NETWORK
    ): Either[String, (NETWORK, Arrow)] =

      for {
        result <- nextSourceArrow.setReference(arrow.nextSourceArrowReference, network)
        (modifiedNetwork, modifiedNextSourceArrowReference) = result
      } yield
        val modifiedArrow = arrow.copy(nextSourceArrowReference = modifiedNextSourceArrowReference)
        (modifiedNetwork, modifiedArrow)

  given [NETWORK](using
    general.arrowReference.ReferencePath[ArrowReference, DotReference, NETWORK]
  ): general.arrow.SetReference[Arrow, ArrowReference, NETWORK] with

    override
    def f(
      arrow: Arrow,
      arrowReference: ArrowReference,
      network: NETWORK
    ): Either[String, (NETWORK, ArrowReference)] =

      arrowReference.referencePath(arrow.sourceDotReference, network)

  given [DOT, NETWORK](using
    general.tail.Delete[Tail, NETWORK],
    general.head.Delete[Head, NETWORK]
  ): general.arrow.Delete[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        network1 <- arrow.tail.delete(network)
        network2 <- arrow.head.delete(network1)
      } yield network2

  given general.arrow.ToTail[Arrow, Tail] with

    override
    def f(
      arrow: Arrow
    ): Tail =

      arrow.tail

  given general.arrow.ToHead[Arrow, Head] with

    override
    def f(
      arrow: Arrow
    ): Head =

      arrow.head

  given [ARROW_REFERENCE, NETWORK](using
    general.tail.GetReferencedBy[Tail, ARROW_REFERENCE, NETWORK]
  ): general.arrow.GetReferencedBy[Arrow, ARROW_REFERENCE, NETWORK] with

    override 
    def f(
      arrow: Arrow, 
      arrowReference: ARROW_REFERENCE, 
      network: NETWORK
    ): Either[String, (NETWORK, ARROW_REFERENCE)] =

      arrow.tail.getReferencedBy(arrowReference, network)