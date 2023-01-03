package net.mem_memov.binet.hexagon.general.tail

trait Follow[TAIL, NETWORK]:

  def f(
    tail: TAIL,
    previousTail: TAIL,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (tail: TAIL)

    def follow(
      previousTail: TAIL,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(tail, previousTail, network)
