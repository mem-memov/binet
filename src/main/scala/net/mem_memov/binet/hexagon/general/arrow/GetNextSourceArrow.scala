package net.mem_memov.binet.hexagon.general.arrow

trait GetNextSourceArrow[ARROW, NETWORK]:
  
  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, ARROW]
  
  extension (arrow: ARROW)
    
    def getNextSourceArrow(
      network: NETWORK
    ): Either[String, ARROW] =

      f(arrow, network)
