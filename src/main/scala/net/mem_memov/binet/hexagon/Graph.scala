package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

class Graph(
  private val network: Network
):

  def vertex: Vertex =
    new Vertex(network, network.dot)

  def vertex(address: memory.Address): Vertex =
    new Vertex(network, network.dot(address))
