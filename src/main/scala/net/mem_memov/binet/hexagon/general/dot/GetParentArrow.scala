package net.mem_memov.binet.hexagon.general.dot

trait GetParentArrow[DOT, ARROW, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, ARROW]

  extension (dot: DOT)

    def getParentArrow(
      network: NETWORK
    ): Either[String, ARROW] =

      f(dot, network)
