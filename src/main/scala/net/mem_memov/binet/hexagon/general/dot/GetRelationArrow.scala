package net.mem_memov.binet.hexagon.general.dot

trait GetRelationArrow[DOT, ARROW, NETWORK]:

  def f(
    dot: DOT,
    network: NETWORK
  ): Either[String, Option[ARROW]]

  extension (dot: DOT)

    def getRelationArrow(
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      f(dot, network)
