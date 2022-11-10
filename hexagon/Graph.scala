package net.mem_memov.binet.memory.hexagon

import zio.*
import net.mem_memov.binet.memory

/**
 * A graph encompasses vertices and edges.
 */
class Graph(
  private val network: Network
):

  def vertex: Task[Vertex] =
    for {
      dot <- network.createDot
    } yield new Vertex(network, dot)


  def vertex(address: memory.Address): Task[Vertex] =
    for {
      dot <- network.getDot(address)
    } yield new Vertex(network, dot)
