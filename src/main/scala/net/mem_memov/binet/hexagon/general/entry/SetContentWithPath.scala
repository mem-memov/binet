package net.mem_memov.binet.hexagon.general.entry

trait SetContentWithPath[ENTRY, NETWORK]:

  def f(
    entry: ENTRY,
    pathEntry: ENTRY,
    network: NETWORK
  ): Either[String, (NETWORK, ENTRY)]

  extension (entry: ENTRY)

    def setContentWithPath(
      pathEntry: ENTRY,
      network: NETWORK
    ): Either[String, (NETWORK, ENTRY)] =

      f(entry, pathEntry, network)
