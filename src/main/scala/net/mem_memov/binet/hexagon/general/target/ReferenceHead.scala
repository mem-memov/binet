package net.mem_memov.binet.hexagon.general.target

trait ReferenceHead[TARGET, DOT_REFERENCE, NETWORK]:

  def f(
    target: TARGET,
    dotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, TARGET)]

  extension (target: TARGET)

    def referenceHead(
      dotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, TARGET)] =

      f(target, dotReference, network)

