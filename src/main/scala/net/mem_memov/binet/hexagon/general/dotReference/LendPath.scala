package net.mem_memov.binet.hexagon.general.dotReference

trait LendPath[DOT_REFERENCE, ENTRY, NETWORK]:

  def f(
    dotReference: DOT_REFERENCE,
    entry: ENTRY,
    network: NETWORK
  ): Either[String, (NETWORK, ENTRY)]

  extension (dotReference: DOT_REFERENCE)

    def lendPath(
      entry: ENTRY,
      network: NETWORK
    ): Either[String, (NETWORK, ENTRY)] =

      f(dotReference, entry, network)
