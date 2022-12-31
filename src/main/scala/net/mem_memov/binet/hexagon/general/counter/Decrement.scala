package net.mem_memov.binet.hexagon.general.counter

trait Decrement[COUNTER, NETWORK]:

  def f(
    counter: COUNTER,
    network: NETWORK
  ): Either[String, (NETWORK, COUNTER)]

  extension (counter: COUNTER)

    def decrement(
      network: NETWORK
    ): Either[String, (NETWORK, COUNTER)] =

      f(counter, network)
