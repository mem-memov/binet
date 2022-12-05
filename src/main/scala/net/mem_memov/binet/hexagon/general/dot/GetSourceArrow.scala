package net.mem_memov.binet.hexagon.general.dot

trait GetSourceArrow[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (dot: DOT)

    def getSourceArrow(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(dot, network)
