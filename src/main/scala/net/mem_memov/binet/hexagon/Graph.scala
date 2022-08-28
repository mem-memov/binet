package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Graph(
  private val network: Network,
  val lastVertex: Option[Vertex]
):

  def vertex: Task[Graph] =
    for {
      updatedNetwork <- network.dot
    } yield new Graph(updatedNetwork, Option(new Vertex(network, network.lastDot)))


  def vertex(address: memory.Address): Vertex =
    new Vertex(network, network.dot(address))
