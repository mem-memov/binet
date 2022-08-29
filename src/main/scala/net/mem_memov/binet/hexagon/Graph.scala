package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Graph(
  private val network: Network
):

  def vertex: Task[Vertex] =
    for {
      dot <- network.dot
    } yield new Vertex(network, dot)


  def vertex(address: memory.Address): Task[Vertex] =
    for {
      dot <- network.dot(address)
    } yield new Vertex(network, dot)
