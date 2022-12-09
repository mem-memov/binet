package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

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

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address, ARROW]
  ): general.dot.GetSourceArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, ARROW] =

      network.readArrow(dot.entry.address5)

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address, ARROW]
  ): general.dot.GetTargetArrow[Dot, ARROW, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, ARROW] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address6)
      } yield modifiedNetwork

