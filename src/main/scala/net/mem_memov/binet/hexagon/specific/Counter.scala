package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Counter(
  entry: Entry
)

object Counter:

  given [NETWORK]: general.counter.Increment[Counter, NETWORK] with

    override
    def f(
      counter: Counter,
      network: NETWORK
    ): Either[String, (NETWORK, Counter)] =

      for {
        incrementResult <- counter.entry.inctementContent(network)
        (modifiedDictionary, modifiedEntry) = incrementResult
      } yield
        val modifiedCounter = counter.copy(entry = modifiedEntry)
        (modifiedDictionary, modifiedCounter)

  given [NETWORK]: general.counter.Decrement[Counter, NETWORK] with

    override
    def f(
      counter: Counter,
      network: NETWORK
    ): Either[String, (NETWORK, Counter)] =

      for {
        decrementResult <- counter.entry.dectementContent(network)
        (modifiedDictionary, modifiedEntry) = decrementResult
      } yield
        val modifiedCounter = counter.copy(entry = modifiedEntry)
        (modifiedDictionary, modifiedCounter)
