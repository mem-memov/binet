package net.mem_memov.binet.memory.hexagon

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

object Edge:

  def apply(network: Network, arrow: Arrow): Edge =
    new Edge(network, arrow)

  def injectVertex(network: Network, sourceDot: Dot, targetDot: Dot): Task[Edge] =
    val combinationZIO = for {
      sourceDotTargetArrow <- sourceDot.targetArrow
      targetDotSourceArrow <- targetDot.sourceArrow
    } yield (sourceDotTargetArrow, targetDotSourceArrow)

    combinationZIO.flatMap {
      case ((Some(sourceDotTargetArrow), Some(targetDotSourceArrow))) =>
        connecting.BothDotsNonEmpty(
          network = network,
          sourceDot = sourceDot,
          targetDot = targetDot,
          sourceDotTargetArrow = sourceDotTargetArrow,
          targetDotSourceArrow = targetDotSourceArrow
        ).connect
      case ((None, Some(targetDotSourceArrow))) =>
        connecting.SourceDotEmpty(
          network = network,
          sourceDot = sourceDot,
          targetDot = targetDot,
          targetDotSourceArrow = targetDotSourceArrow
        ).connect
      case ((Some(sourceDotTargetArrow), None)) =>
        connecting.TargetDotEmpty(
          network = network,
          sourceDot = sourceDot,
          targetDot = targetDot,
          sourceDotTargetArrow = sourceDotTargetArrow,
        ).connect
      case ((None, None)) =>
        connecting.BothDotsEmpty(
          network = network,
          sourceDot = sourceDot,
          targetDot = targetDot,
        ).connect
    }.map { newArrow =>
      Edge(network, newArrow)
    }