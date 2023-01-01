package net.mem_memov.binet.hexagon.general.dotReference

trait ReferencePath[DOT_REFERENCE, NETWORK]:

  def f(
    dotReference: DOT_REFERENCE,
    mentionedDotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, DOT_REFERENCE)]

  extension (dotReference: DOT_REFERENCE)

    def referencePath(
      mentionedDotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, DOT_REFERENCE)] =

      f(dotReference, mentionedDotReference, network)
