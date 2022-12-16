package net.mem_memov.binet.hexagon.general.dot

trait IncrementTargetCount[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (dot: DOT)

    def incrementTargetCount(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(dot, network)
