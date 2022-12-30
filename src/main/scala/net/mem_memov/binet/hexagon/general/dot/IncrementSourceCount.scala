package net.mem_memov.binet.hexagon.general.dot

trait IncrementSourceCount[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, (NETWORK, DOT)]

  extension (dot: DOT)

    def incrementSourceCount(
      network: NETWORK
    ): Either[String, (NETWORK, DOT)] =

      f(dot, network)
