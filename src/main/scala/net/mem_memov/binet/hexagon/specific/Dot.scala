package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

case class Dot(
  address: Address,
  entry: Entry
)

object Dot:

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
    general.network.ReadArrow[NETWORK, Address]
  ): general.dot.GetParentArrow[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address1)
      } yield modifiedNetwork

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.dot.GetChildArrow[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address2)
      } yield modifiedNetwork

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.dot.GetSourceArrow[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address5)
      } yield modifiedNetwork

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.dot.GetTargetArrow[Dot, NETWORK] with

    override
    def f(
      dot: Dot,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(dot.entry.address6)
      } yield modifiedNetwork

