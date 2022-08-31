package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import zio.*

/**
 * An edge of a graph.
 */
class Edge(
  private val network: Network,
  private val arrow: Arrow
):
  
  def findBySourceDot(sourceDot: Dot): Task[Option[Edge]] =
    if arrow.hasSourceDot(sourceDot) then
      ZIO.succeed(Some(this))
    else
      for {
        nextSourceArrow <- arrow.nextSourceArrow
        nextSourceEdge <- nextSourceArrow match
          case Some(nextArrow) => Edge(network, nextArrow).findBySourceDot(sourceDot)
          case None => ZIO.succeed(Option.empty[Edge])
      } yield nextSourceEdge
  
  def findByTargetDot(targetDot: Dot): Task[Option[Edge]] =
    if arrow.hasTargetDot(targetDot) then
      ZIO.succeed(Some(this))
    else
      for {
        nextTargetArrow <- arrow.nextTargetArrow
        nextTargetEdge <- nextTargetArrow match
          case Some(nextArrow) => Edge(network, nextArrow).findByTargetDot(targetDot)
          case None => ZIO.succeed(Option.empty[Edge])
      } yield nextTargetEdge

  def injectSourceVertex(sourceDot: Dot, targetDot: Dot): Task[Edge] =
    val combinationZIO = for {
      sourceDotTargetArrow <- sourceDot.targetArrow
      targetDotSourceArrow <- targetDot.sourceArrow
    } yield (sourceDotTargetArrow, targetDotSourceArrow)

    val connectStrategyZIO = combinationZIO.flatMap {
      case ((Some(sourceDotTargetArrow), Some(targetDotSourceArrow))) =>
        BothDotsNonEmpty(
          sourceDotTargetArrow,
          targetDotSourceArrow
        )
      case ((None, Some(targetDotSourceArrow))) =>
        TargetDotNonEmpty(
          targetDotSourceArrow
        )
      case ((Some(sourceDotTargetArrow), None)) =>
        SourceDotNonEmpty(
          sourceDotTargetArrow
        )
      case ((None, None)) =>
        BothDotsEmpty
    }

    // in -> E -> out
    val entryZIO = combinationZIO.flatMap {
      case ((Some(incomingArrow), Some(outgoingArrow))) =>
        val entry = Entry(
          sourceDot.address,
          incomingArrow.address,
          memory.Address.zero,
          targetDot.address,
          outgoingArrow.address,
          memory.Address.zero
        )
      case ((None, Some(outgoingArrow))) =>
        val entry = Entry(
          sourceDot.address,
          memory.Address.zero,
          memory.Address.zero,
          targetDot.address,
          outgoingArrow.address,
          memory.Address.zero
        )
      case ((Some(incomingArrow), None)) =>
        val entry = Entry(
          sourceDot.address,
          incomingArrow.address,
          memory.Address.zero,
          targetDot.address,
          memory.Address.zero,
          memory.Address.zero
        )
      case ((None, None)) =>
        val entry = Entry(
          sourceDot.address,
          memory.Address.zero,
          memory.Address.zero,
          targetDot.address,
          memory.Address.zero,
          memory.Address.zero
        )
    }

    val arrowZIO = for {
      newEntry <- entryZIO
      newArrow <- network.createArrow(entry)
    } yield newArrow match
    ???


object Edge:

  def apply(network: Network, arrow: Arrow): Edge =
    new Edge(network, arrow)
