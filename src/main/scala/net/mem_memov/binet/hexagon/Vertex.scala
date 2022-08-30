package net.mem_memov.binet.hexagon

import zio.*

class Vertex(
  private val network: Network,
  private[Vertex] val dot: Dot
):

  def hasSource(source: Vertex): Task[Option[Edge]] =
    for {
      sourceArrow <- dot.sourceArrow
    } yield sourceArrow.map(Edge(network, _))

  def addSource(source: Vertex): Task[Option[Edge]] =
    for {
      sourceEdge <- hasSource(source)
    } yield sourceEdge match {
      case option @ Some(edge) => option
      case None => for {
        sourceArrow <- dot.sourceArrow
        _ <- sourceArrow match {
          case Some(arrow) => ???
          case None => ???
        }
      } yield ???
    }
//    if hasSource(source) then
//      Some(this)
//    else
//
//      ???

  def removeSource(source: Vertex): Option[Vertex] = ???


  def hasTarget(target: Vertex): Boolean = ???

  def addTarget(target: Vertex): Option[Vertex] = ???

  def removeTarget(target: Vertex): Option[Vertex] = ???


object Vertex:

  def apply(network: Network, dot: Dot): Vertex =
    new Vertex(network, dot)
