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

  def removeSource(source: Vertex): Option[Vertex] = ???

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

  def addTarget(target: Vertex): Task[Option[Edge]] = ???
//    for {
//      targetEdge <- hasTarget(target)
//    } yield targetEdge match {
//      case optionEdge @ Some(_) => optionEdge
//      case None => for {
//        targetArrow <- dot.targetArrow
//        _ <- targetArrow match {
//          case Some(arrow) => Edge(network, arrow).injectSourceVertex(source.dot, dot)
//          case None => ???
//        }
//      } yield ???
//    }

  def removeTarget(target: Vertex): Option[Vertex] = ???


object Vertex:

  def apply(network: Network, dot: Dot): Vertex =
    new Vertex(network, dot)
