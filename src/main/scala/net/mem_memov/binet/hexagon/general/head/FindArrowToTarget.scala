package net.mem_memov.binet.hexagon.general.head

trait FindArrowToTarget[HEAD, ARROW, NETWORK, TARGET]:
  
  def f(
    head: HEAD,
    target: TARGET,
    network: NETWORK
  ): Either[String, Option[ARROW]]
  
  extension (head: HEAD)
    
    def findArrowToTarget(
      target: TARGET,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      f(head, target, network)
