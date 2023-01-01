package net.mem_memov.binet.hexagon.general.dot

trait GetNextDot[DOT, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, Option[DOT]]

  extension (dot: DOT)

    def getNextDot(
      network: NETWORK
    ): Either[String, Option[DOT]] =

      f(dot, network)
