package net.mem_memov.binet.hexagon.general.head

trait CollectTargets[HEAD, NETWORK, TARGET]:

  def f(
    head: HEAD,
    network: NETWORK,
    targets: List[TARGET]
  ): Either[String, List[TARGET]]

  extension (head: HEAD)

    def collectTargets(
      network: NETWORK,
      targets: List[TARGET]
    ): Either[String, List[TARGET]] =

      f(head, network, targets)
