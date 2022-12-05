package net.mem_memov.binet.hexagon.general.dot

trait GetParentArrow[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (dot: DOT)

    def getParentArrow(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(dot, network)
