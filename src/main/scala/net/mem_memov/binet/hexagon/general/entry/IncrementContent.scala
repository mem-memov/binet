package net.mem_memov.binet.hexagon.general.entry

trait IncrementContent[ENTRY, NETWORK]:

  def f(
    entry: ENTRY,
    network: NETWORK
  ): Either[String, (NETWORK, ENTRY)]

  extension (entry: ENTRY)

    def incrementContent(
      network: NETWORK
    ): Either[String, (NETWORK, ENTRY)] =

      f(entry, network)
