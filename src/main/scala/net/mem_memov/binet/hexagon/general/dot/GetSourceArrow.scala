package net.mem_memov.binet.hexagon.general.dot

trait GetSourceArrow[DOT, ARROW, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, ARROW]

  extension (dot: DOT)

    def getSourceArrow(
      network: NETWORK
    ): Either[String, ARROW] =

      f(dot, network)
