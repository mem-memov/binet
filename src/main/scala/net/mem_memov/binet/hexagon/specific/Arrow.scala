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

  given general.arrow.HasSourceDot[Arrow, Address] with

    override
    def f(
      arrow: Arrow,
      sourceDotAddress: Address
    ): Boolean =

      arrow.entry.address1 == sourceDotAddress

  given general.arrow.HasTargetDot[Arrow, Address] with

    override
    def f(
      arrow: Arrow,
      targetDotAddress: Address
    ): Boolean =

      arrow.entry.address4 == targetDotAddress

  given [NETWORK](using
    general.network.UpdateEntry[NETWORK, Arrow]
  ): general.arrow.SetNextSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      nextSourceArrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      val modifiedEntry = arrow.entry.copy(address3 = arrow.address)
      val modifiedArrow = arrow.copy(entry = modifiedEntry)

      for {
        modifiedNetwork <- network.updateEntry(modifiedArrow)
      } yield modifiedNetwork

