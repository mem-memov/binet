package net.mem_memov.binet.hexagon.general.dotReference

trait ReadSource[DOT_REFERENCE, NETWORK, SOURCE]:
  
  def f(
    dotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, SOURCE]
  
  extension (dotReference: DOT_REFERENCE)
    
    def readSource(
      network: NETWORK
    ): Either[String, SOURCE] =

      f(dotReference, network)
