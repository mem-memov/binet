package net.mem_memov.binet.memory.hexagon

import zio.*

/**
 * A vertex of a graph.
 */
class Vertex(
  private val network: Network,
  private[Vertex] val dot: Dot
):

  def hasSource(source: Vertex): Task[Option[Edge]] =
    dot.sourceArrow.flatMap { firstSourceArrowOption =>
      firstSourceArrowOption match
        case Some(firstSourceArrow) =>
          firstSourceArrow.search.withSourceDot(source.dot).map { sourceArrowOption =>
            sourceArrowOption.map { sourceArrow =>
              Edge(network, sourceArrow)
            }
          }
        case None => ZIO.succeed(None)
    }

  def hasTarget(target: Vertex): Task[Option[Edge]] =
    dot.targetArrow.flatMap { firstTargetArrowOption =>
      firstTargetArrowOption match
        case Some(firstTargetArrow) =>
          firstTargetArrow.search.withTargetDot(target.dot).map { targetArrowOption =>
            targetArrowOption.map { targetArrow =>
              Edge(network, targetArrow)
            }
          }
        case None => ZIO.succeed(None)
    }

  def addTarget(target: Vertex): Task[Edge] =
    for {
      targetEdgeOption <- hasTarget(target)
      targetEdge <- targetEdgeOption match
        case Some(targetEdge) => ZIO.succeed(targetEdge)
        case None => Edge.injectVertex(network, dot, target.dot)
    } yield targetEdge

  def addSource(source: Vertex): Task[Edge] =
    for {
      sourceEdgeOption <- hasSource(source)
      sourceEdge <- sourceEdgeOption match
        case Some(sourceEdge) => ZIO.succeed(sourceEdge)
        case None => Edge.injectVertex(network, source.dot, dot)
    } yield sourceEdge

  def removeSource(source: Vertex): Option[Vertex] = ???

  def removeTarget(target: Vertex): Option[Vertex] = ???


object Vertex:

  def apply(network: Network, dot: Dot): Vertex =
    new Vertex(network, dot)

