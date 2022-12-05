package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  address: Address,
  entry: Entry
)

object Arrow:

  given [DOT, NETWORK](using
    general.network.ReadDot[NETWORK, Address]
  ): general.arrow.GetSourceDot[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readDot(arrow.entry.address1)
      } yield modifiedNetwork

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.arrow.GetPreviousSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(arrow.entry.address2)
      } yield modifiedNetwork

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.arrow.GetNextSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(arrow.entry.address3)
      } yield modifiedNetwork

  given [DOT, NETWORK](using
    general.network.ReadDot[NETWORK, Address]
  ): general.arrow.GetTargetDot[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readDot(arrow.entry.address4)
      } yield modifiedNetwork

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.arrow.GetPreviousTargetArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(arrow.entry.address5)
      } yield modifiedNetwork

  given [NETWORK](using
    general.network.ReadArrow[NETWORK, Address]
  ): general.arrow.GetNextTargetArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        modifiedNetwork <- network.readArrow(arrow.entry.address6)
      } yield modifiedNetwork


