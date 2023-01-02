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
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference],
    general.network.ReadDot[NETWORK, DOT, DotReference],
    general.arrow.SetNextSourceArrow[Arrow, NETWORK],
    general.arrow.SetPreviousSourceArrow[Arrow, NETWORK],
    general.arrow.DeleteNextSourceArrow[Arrow, NETWORK],
    general.arrow.DeletePreviousSourceArrow[Arrow, NETWORK],
    general.arrow.SetNextTargetArrow[Arrow, NETWORK],
    general.arrow.SetPreviousTargetArrow[Arrow, NETWORK],
    general.arrow.DeleteNextTargetArrow[Arrow, NETWORK],
    general.arrow.DeletePreviousTargetArrow[Arrow, NETWORK],
    general.dot.SetSourceArrow[DOT, Arrow, NETWORK],
    general.dot.DeleteSourceArrow[DOT, NETWORK],
    general.dot.SetTargetArrow[DOT, Arrow, NETWORK],
    general.dot.DeleteTargetArrow[DOT, NETWORK]
  ): general.arrow.Delete[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        sourceDot <- network.readDot(arrow.sourceDotReference)
        previousSourceArrowOption <- network.readArrow(arrow.previousSourceArrowReference)
        nextSourceArrowOption <- network.readArrow(arrow.nextSourceArrowReference)
        targetDot <- network.readDot(arrow.targetDotReference)
        previousTargetArrowOption <- network.readArrow(arrow.previousTargetArrowReference)
        nextTargetArrowOption <- network.readArrow(arrow.nextTargetArrowReference)
        modifiedSourceNetwork <- previousSourceArrowOption match
          case Some(previousSourceArrow) =>
            nextSourceArrowOption match
              case Some(nextSourceArrow) =>
                for {
                  setNextSourceArrowResult <- previousSourceArrow.setNextSourceArrow(nextSourceArrow, network)
                  (network1, _) = setNextSourceArrowResult
                  setPreviousSourceArrowResult <- nextSourceArrow.setPreviousSourceArrow(previousSourceArrow, network1)
                  (network2, _) = setPreviousSourceArrowResult
                } yield network2
              case None =>
                for {
                  deleteNextSourceArrowResult <- previousSourceArrow.deleteNextSourceArrow(network)
                  (network1, _) = deleteNextSourceArrowResult
                } yield network1
          case None =>
            nextSourceArrowOption match
              case Some(nextSourceArrow) =>
                for {
                  setSourceArrowResult <- sourceDot.setSourceArrow(nextSourceArrow, network)
                  (network1, _) = setSourceArrowResult
                  deletePreviousSourceArrowResult <- nextSourceArrow.deletePreviousSourceArrow(network1)
                  (network2, _) = deletePreviousSourceArrowResult
                } yield network2
              case None =>
                for {
                  deleteSourceArrowResult <- sourceDot.deleteSourceArrow(network)
                  (network1, _) = deleteSourceArrowResult
                } yield network1
        modifiedTargetNetwork <- previousTargetArrowOption match
          case Some(previousTargetArrow) =>
            nextTargetArrowOption match
              case Some(nextTargetArrow) =>
                for {
                  setNextTargetArrowResult <- previousTargetArrow.setNextTargetArrow(nextTargetArrow, modifiedSourceNetwork)
                  (network1, _) = setNextTargetArrowResult
                  setPreviousTargetArrowResult <- nextTargetArrow.setPreviousTargetArrow(previousTargetArrow, network1)
                  (network2, _) = setPreviousTargetArrowResult
                } yield network2
              case None =>
                for {
                  deleteNextTargetArrowResult <- previousTargetArrow.deleteNextTargetArrow(modifiedSourceNetwork)
                  (network1, _) = deleteNextTargetArrowResult
                } yield network1
          case None =>
            nextTargetArrowOption match
              case Some(nextTargetArrow) =>
                for {
                  setTargetArrowResult <- targetDot.setTargetArrow(nextTargetArrow, modifiedSourceNetwork)
                  (network1, _) = setTargetArrowResult
                  deletePreviousTargetArrowResult <- nextTargetArrow.deletePreviousTargetArrow(network1)
                  (network2, _) = deletePreviousTargetArrowResult
                } yield network2
              case None =>
                for {
                  deleteTargetArrowResult <- targetDot.deleteTargetArrow(modifiedSourceNetwork)
                  (network1, _) = deleteTargetArrowResult
                } yield network1
      } yield modifiedTargetNetwork
