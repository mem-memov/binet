package net.mem_memov.binet.hexagon.general.target

trait IsInHeads[TARGET, HEAD, NETWORK]:

  def f(
    target: TARGET,
    head: HEAD,
    network: NETWORK
  ): Either[String, Boolean]

  extension (target: TARGET)

    def isInHeads(
      head: HEAD,
      network: NETWORK
    ) =

      f(target, head, network)
