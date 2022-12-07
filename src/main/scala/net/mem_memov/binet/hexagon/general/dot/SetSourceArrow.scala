package net.mem_memov.binet.hexagon.general.dot

trait SetSourceArrow[DOT, ARROW, NETWORK]:

  def f(
    dot: DOT,
    arrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (dot: DOT)

    def setSourceArrow(
      arrow: ARROW,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(dot, arrow, network)
