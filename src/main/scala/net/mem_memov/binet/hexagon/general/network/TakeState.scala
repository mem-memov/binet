package net.mem_memov.binet.hexagon.general.network

trait TakeState[NETWORK]:

  def f(
    network: NETWORK,
    donor: NETWORK
  ): NETWORK

  extension (network: NETWORK)

    def takeState(
      donor: NETWORK
    ): NETWORK =

      f(network, donor)
