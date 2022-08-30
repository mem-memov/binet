package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import zio.*

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

    combinationZIO.flatMap {
      case ((Some(incomingArrow), Some(outgoingArrow))) => ??? // in -> E -> out
      case ((None, Some(outgoingArrow))) => ???
      case ((Some(incomingArrow), None)) => ???
      case ((None, None)) => ???
    }
    ???


object Edge:

  def apply(network: Network, arrow: Arrow): Edge =
    new Edge(network, arrow)
