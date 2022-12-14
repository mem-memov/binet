package net.mem_memov.binet.hexagon.general.network

trait CreateDot[NETWORK, DOT]:
  
  def f(
    network: NETWORK
  ): Either[String, (NETWORK, DOT)]
  
  extension (network: NETWORK)
    
    def createDot(): Either[String, (NETWORK, DOT)] =

      f(network)
