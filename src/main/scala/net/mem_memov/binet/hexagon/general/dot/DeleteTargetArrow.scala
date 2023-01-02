package net.mem_memov.binet.hexagon.general.dot

trait DeleteTargetArrow[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, (NETWORK, DOT)]

  extension (dot: DOT)

    def deleteTargetArrow(
      network: NETWORK
    ): Either[String, (NETWORK, DOT)] =

      f(dot, network)
