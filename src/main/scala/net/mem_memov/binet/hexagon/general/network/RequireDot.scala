package net.mem_memov.binet.hexagon.general.network

trait RequireDot[NETWORK, DOT]:

  def f(
    network: NETWORK
  ): Either[String, DOT]

  extension(network: NETWORK)

    def requireDot(): Either[String, DOT] =

      f(network)