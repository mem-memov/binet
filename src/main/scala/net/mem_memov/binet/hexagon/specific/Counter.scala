package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Counter(
  entry: Entry
)

object Counter:

  given [NETWORK](using
    general.entry.IncrementContent[Entry, NETWORK]
  ): general.counter.Increment[Counter, NETWORK] with

    override
    def f(
      counter: Counter,
      network: NETWORK
    ): Either[String, (NETWORK, Counter)] =

      for {
        incrementResult <- counter.entry.incrementContent(network)
        (modifiedDictionary, modifiedEntry) = incrementResult
      } yield
        val modifiedCounter = counter.copy(entry = modifiedEntry)
        (modifiedDictionary, modifiedCounter)

  given [NETWORK](using
    general.entry.DecrementContent[Entry, NETWORK]
  ): general.counter.Decrement[Counter, NETWORK] with

    override
    def f(
      counter: Counter,
      network: NETWORK
    ): Either[String, (NETWORK, Counter)] =

      for {
        decrementResult <- counter.entry.decrementContent(network)
        (modifiedDictionary, modifiedEntry) = decrementResult
      } yield
        val modifiedCounter = counter.copy(entry = modifiedEntry)
        (modifiedDictionary, modifiedCounter)

  given (using
    general.entry.ContentIsLarger[Entry]
  ): general.counter.IsLarger[Counter] with

    override
    def f(
      counter: Counter,
      theOther: Counter
    ): Boolean =

      counter.entry.contentIsLarger(theOther.entry)
