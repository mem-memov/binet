package net.mem_memov.binet.hexagon.general.network

trait HasDot[NETWORK]:

  def f(
    network: NETWORK
  ): Boolean

  extension (network: NETWORK)

    def hasADot: Boolean =

      f(network)
