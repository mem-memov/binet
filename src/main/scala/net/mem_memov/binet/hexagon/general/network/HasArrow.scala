package net.mem_memov.binet.hexagon.general.network

trait HasArrow[NETWORK]:
  
  def f(
    network: NETWORK
  ): Boolean
  
  extension (network: NETWORK)
    
    def hasArrow: Boolean =

      f(network)