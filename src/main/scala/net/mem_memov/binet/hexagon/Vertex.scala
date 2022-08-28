package net.mem_memov.binet.hexagon

class Vertex(
  private val network: Network,
  private[Vertex] val dot: Dot
):

  def hasSource(source: Vertex): Boolean = ???
//    dot.sourceArrow.map(Edge(network, _)).map { arrow =>
//      arrow.findSource { arrow =>
//        arrow.hasSourceDot(source.dot)
//      }
//    }

  def addSource(source: Vertex): Option[Vertex] = ???
//    if hasSource(source) then
//      Some(this)
//    else
//
//      ???

  def removeSource(source: Vertex): Option[Vertex] = ???


  def hasTarget(target: Vertex): Boolean = ???

  def addTarget(target: Vertex): Option[Vertex] = ???

  def removeTarget(target: Vertex): Option[Vertex] = ???

