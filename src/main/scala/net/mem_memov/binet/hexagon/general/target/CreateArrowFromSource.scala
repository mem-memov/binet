package net.mem_memov.binet.hexagon.general.target

trait CreateArrowFromSource[TARGET, ARROW, ARROW_DRAFT_BEGIN, NETWORK]:

  def f(
    target: TARGET,
    arrowDraftBegin: ARROW_DRAFT_BEGIN,
    network: NETWORK
  ): Either[String, (NETWORK, TARGET, ARROW)]

  extension (target: TARGET)

    def createArrowFromSource(
      arrowDraftBegin: ARROW_DRAFT_BEGIN,
      network: NETWORK
    ): Either[String, (NETWORK, TARGET, ARROW)] =

      f(target, arrowDraftBegin, network)
