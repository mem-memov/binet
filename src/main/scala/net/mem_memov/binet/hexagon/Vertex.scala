package net.mem_memov.binet.hexagon

import zio.*

/**
 * A vertex of a graph.
 */
class Vertex(
  private val network: Network,
  private[Vertex] val dot: Dot
):

  def hasSource(source: Vertex): Task[Option[Edge]] =
    for {
      sourceArrow <- dot.sourceArrow
    } yield sourceArrow.map(Edge(network, _))


  def hasTarget(source: Vertex): Task[Option[Edge]] =
    for {
      targetArrow <- dot.targetArrow
    } yield targetArrow.map(Edge(network, _))

  def addTarget(target: Vertex): Task[Option[Edge]] =
    for {
      targetEdge <- hasTarget(target)
    } yield targetEdge match {
      case optionEdge @ Some(_) => optionEdge
      case None => for {
        targetArrow <- dot.targetArrow
        _ <- targetArrow match {
          case Some(arrow) => Edge(network, arrow).injectSourceVertex(source.dot, dot)
          case None => ???
        }
      } yield ???
    }


  def removeSource(source: Vertex): Option[Vertex] = ???


  def hasTarget(target: Vertex): Boolean = ???

  def addTarget(target: Vertex): Option[Vertex] = ???

  def removeTarget(target: Vertex): Option[Vertex] = ???


object Vertex:

  def apply(network: Network, dot: Dot): Vertex =
    new Vertex(network, dot)
