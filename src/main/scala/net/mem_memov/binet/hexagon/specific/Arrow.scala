package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general.dotReference.InArrow
import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  sourceDotReference: DotReference,
  previousSourceArrowReference: ArrowReference,
  nextSourceArrowReference: ArrowReference,
  targetDotReference: DotReference,
  previousTargetArrowReference: ArrowReference,
  nextTargetArrowReference: ArrowReference
)

object Arrow:

  given (using
    general.dotReference.InArrow[DotReference]
  ): general.arrow.IsArrow[Arrow] with

    override def f(arrow: Arrow): Boolean =

      arrow.sourceDotReference.inArrow

  given [DOT, NETWORK](using
    general.network.ReadDot[NETWORK, DotReference, DOT]
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
    general.network.ReadDot[NETWORK, DotReference, DOT]
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
