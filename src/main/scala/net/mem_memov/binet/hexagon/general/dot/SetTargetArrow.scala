package net.mem_memov.binet.hexagon.general.dot

trait SetTargetArrow[DOT, ARROW, NETWORK]:

  def f(
    dot: DOT,
    arrow: ARROW,
    network: NETWORK
  ): Either[String, (NETWORK, DOT)]

  extension (dot: DOT)

    def setTargetArrow(
      arrow: ARROW,
      network: NETWORK
    ): Either[String, (NETWORK, DOT)] =

      f(dot, arrow, network)
