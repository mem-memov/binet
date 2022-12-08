package net.mem_memov.binet.hexagon.specific.arrow.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory

class Fetcher

object Fetcher:

  given [ADDRESS, ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, ADDRESS, ARROW],
    memory.general.address.IsZero[ADDRESS]
  ): specific.arrow.general.fetcher.FetchArrow[Fetcher, ADDRESS, ARROW, NETWORK] with

    override
    def f(
      address: ADDRESS,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      if address.isZero then
        Right(None)
      else
        for {
          arrow <- network.readArrow(address)
        } yield Some(arrow)


