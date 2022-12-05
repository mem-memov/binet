package net.mem_memov.binet.hexagon.general.network

trait UpdateDot[NETWORK, DOT]:
  
  def f(
    network: NETWORK,
    dot: DOT
  ): Either[String, NETWORK]
  
  extension (network: NETWORK)
    
    def updateDot(
      dot: DOT
    ): Either[String, NETWORK] =

      f(network, dot)
