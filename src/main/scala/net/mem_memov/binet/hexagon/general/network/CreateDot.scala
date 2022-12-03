package net.mem_memov.binet.hexagon.general.network

trait CreateDot[NETWORK]:
  
  def f(
    network: NETWORK
  ): Either[String, NETWORK]
  
  extension (network: NETWORK)
    
    def createDot(): Either[String, NETWORK] =

      f(network)
