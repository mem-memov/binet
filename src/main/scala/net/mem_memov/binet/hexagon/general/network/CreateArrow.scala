package net.mem_memov.binet.hexagon.general.network

trait CreateArrow[NETWORK, ARROW, ARROW_DRAFT_END]:

  def f(
    network: NETWORK,
    arrowDraftEnd: ARROW_DRAFT_END
  ): Either[String, (NETWORK, ARROW)]

  extension (network: NETWORK)

    def createArrow(
      arrowDraftEnd: ARROW_DRAFT_END
    ): Either[String, (NETWORK, ARROW)] =

      f(network, arrowDraftEnd)
