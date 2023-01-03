package net.mem_memov.binet.hexagon.general.head

trait FindTarget[HEAD, NETWORK, TARGET]:
  
  def f(
    head: HEAD,
    target: TARGET,
    network: NETWORK
  ): Either[String, Option[HEAD]]
  
  extension (head: HEAD)
    
    def findTarget(
      target: TARGET,
      network: NETWORK
    ): Either[String, Option[HEAD]] =

      f(head, target, network)
