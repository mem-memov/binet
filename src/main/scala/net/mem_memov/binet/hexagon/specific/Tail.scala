package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Tail(
  dotReference: DotReference,
  previousArrowReference: ArrowReference,
  nextArrowReference: ArrowReference
)

object Tail:

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.arrow.ToTail[Arrow, Tail]
  ): general.tail.GetNext[Tail, NETWORK] with

    override
    def f(
      tail: Tail,
      network: NETWORK
    ): Either[String, Option[Tail]] =

      for {
        optionArrow <- network.readArrow(tail.nextArrowReference)
      } yield optionArrow.toTail

  given [SOURCE](using
    general.source.InArrowTail[SOURCE, Arrow]
  ): general.tail.HasSource[Tail, SOURCE] with

    override
    def f(
      tail: Tail,
      source: SOURCE
    ): Boolean =

      source.inArrowTail(tail.arrow)
      
      
  given [ARROW, NETWORK, SOURCE]: general.tail.FindArrow[Tail, ARROW, NETWORK, SOURCE] with

    override 
    def f(
      tail: Tail, 
      source: SOURCE, 
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      val dotEither = network.readDot(tail.dotReference)
      dotEither match
        case Left(error) => Left(error)
        case Right(dot) => ???
      
      
//      dotEither match
//        case Left(error) => Left(error)
//        case Right(dot) =>
//          val nextArrowOptionEither = network.readArrow(tail.nextArrowReference)
//          nextArrowOptionEither match
//            case Left(error) => Left(error)
//            case Right(nextArrowOption) =>
//              nextArrowOption match
//                case Some(nextArrow) => f(nextArrow.toTail, network, dot.toSource :: sources)
//                case None => Right(dot.toSource :: sources)

  given [ARROW, DOT, NETWORK, SOURCE](using
    general.network.ReadDot[NETWORK, DOT, DotReference],
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.dot.ToSource[DOT, SOURCE],
    general.arrow.ToTail[ARROW, Tail]
  ): general.tail.CollectSources[Tail, NETWORK, SOURCE] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      tail: Tail,
      network: NETWORK,
      sources: List[SOURCE]
    ): Either[String, List[SOURCE]] =

      val dotEither = network.readDot(tail.dotReference)
      dotEither match
        case Left(error) => Left(error)
        case Right(dot) =>
          val nextArrowOptionEither = network.readArrow(tail.nextArrowReference)
          nextArrowOptionEither match
            case Left(error) => Left(error)
            case Right(nextArrowOption) =>
              nextArrowOption match
                case Some(nextArrow) => f(nextArrow.toTail, network, dot.toSource :: sources)
                case None => Right(dot.toSource :: sources)

  given [DOT, NETWORK](using
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference],
    general.network.ReadDot[NETWORK, DOT, DotReference],
    general.arrow.SetNextSourceArrow[Arrow, NETWORK],
    general.arrow.SetPreviousSourceArrow[Arrow, NETWORK],
    general.arrow.DeleteNextSourceArrow[Arrow, NETWORK],
    general.arrow.DeletePreviousSourceArrow[Arrow, NETWORK],
    general.dot.SetSourceArrow[DOT, Arrow, NETWORK],
    general.dot.DeleteSourceArrow[DOT, NETWORK]
  ): general.tail.Delete[Tail, NETWORK] with

    override
    def f(
      tail: Tail,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        sourceDot <- network.readDot(tail.dotReference)
        previousSourceArrowOption <- network.readArrow(tail.previousArrowReference)
        nextSourceArrowOption <- network.readArrow(tail.nextArrowReference)
        modifiedNetwork <- previousSourceArrowOption match
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
      } yield modifiedNetwork