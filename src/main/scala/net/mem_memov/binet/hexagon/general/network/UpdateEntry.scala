package net.mem_memov.binet.hexagon.general.network

trait UpdateEntry[NETWORK, ENTRY]:

  def f(
    network: NETWORK,
    entry: ENTRY
  ): Either[String, NETWORK]

  extension (network: NETWORK)

    def updateEntry(
      entry: ENTRY
    ): Either[String, NETWORK] =

      f(network, entry)
