package net.mem_memov.binet.hexagon.general.network

trait GetDot[NETWORK, DOT]:

  def f(
    network: NETWORK
  ): Either[String, DOT]

  extension(network: NETWORK)

    def getDot(): Either[String, DOT] =

      f(network)