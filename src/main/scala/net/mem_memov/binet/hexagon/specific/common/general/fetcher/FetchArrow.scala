package net.mem_memov.binet.hexagon.specific.common.general.fetcher

trait FetchArrow[FETCHER, ADDRESS, ARROW, NETWORK]:

  def f(
    address: ADDRESS,
    network: NETWORK
  ): Either[String, Option[ARROW]]

  extension (fetcher: FETCHER)

    def fetchArrow(
      address: ADDRESS,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      f(address, network)
