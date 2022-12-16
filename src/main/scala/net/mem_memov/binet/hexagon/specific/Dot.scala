package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Dot(
  address: Address,
  entry: Entry
)

object Dot:

  given (using
    general.entry.GetAddress1[Entry, Address]
  ): general.dot.IsDot[Dot] with

    override def f(dot: Dot): Boolean =

      dot.address == dot.entry.address1

  given general.dot.GetAddress[Dot, Address] with

    override
    def f(
      dot: Dot
    ): Address =

      dot.address

  given general.dot.GetEntry[Dot, Entry] with

    override
    def f(
      dot: Dot
    ): Entry =

      dot.entry

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address, ARROW]
  ): general.dot.GetParentArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, ARROW] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address1)
      } yield modifiedNetwork

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address, ARROW]
  ): general.dot.GetChildArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, ARROW] =

      network.readArrow(dot.entry.address2)

  given [ARROW, FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, ARROW, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.dot.GetSourceArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      fetcher.fetchArrow(dot.entry.address5, network)

  given [ARROW, FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, ARROW, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.dot.GetTargetArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      fetcher.fetchArrow(dot.entry.address6, network)

  given (using
    general.entry.GetAddress3[Entry, Address]
  ): general.dot.GetSourceCount[Dot, Address] with

    override
    def f(
      dot: Dot
    ): Address =

      dot.entry.getAddress3

  given (using
    general.entry.GetAddress4[Entry, Address]
  ): general.dot.GetTargetCount[Dot, Address] with

    override
    def f(
      dot: Dot
    ): Address =

      dot.entry.getAddress4

  given [NETWORK](using
    general.entry.GetAddress3[Entry, Address],
    general.entry.SetAddress3[Entry, Address],
    memory.general.address.Increment[Address],
    general.network.UpdateDot[NETWORK, Dot]
  ): general.dot.IncrementSourceCount[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, NETWORK] =

      val modifiedCount = dot.entry.getAddress3.increment()
      val modifiedEntry = dot.entry.setAddress3(modifiedCount)
      val modifiedDot = dot.copy(entry = modifiedEntry)

      network.updateDot(modifiedDot)

  given [NETWORK](using
    general.entry.GetAddress4[Entry, Address],
    general.entry.SetAddress4[Entry, Address],
    memory.general.address.Increment[Address],
    general.network.UpdateDot[NETWORK, Dot]
  ): general.dot.IncrementTargetCount[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, NETWORK] =

      val modifiedCount = dot.entry.getAddress4.increment()
      val modifiedEntry = dot.entry.setAddress4(modifiedCount)
      val modifiedDot = dot.copy(entry = modifiedEntry)

      network.updateDot(modifiedDot)


