package net.mem_memov.binet.hexagon.general.entry

trait DecrementContent[ENTRY, NETWORK]:

  def f(
    entry: ENTRY,
    network: NETWORK
  ): Either[String, (NETWORK, ENTRY)]

  extension (entry: ENTRY)

    def decrementContent(
      network: NETWORK
    ): Either[String, (NETWORK, ENTRY)] =

      f(entry, network)
