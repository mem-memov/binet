package net.mem_memov.binet.hexagon.general.dot

trait GetChildArrow[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (dot: DOT)

    def getChildArrow(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(dot, network)
