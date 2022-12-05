package net.mem_memov.binet.hexagon.general.dot

trait GetTargetArrow[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (dot: DOT)

    def getTargetArrow(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(dot, network)
