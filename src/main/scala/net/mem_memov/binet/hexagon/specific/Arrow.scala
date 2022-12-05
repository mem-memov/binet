package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  address: Address,
  entry: Entry
)

object Arrow:

  given general.arrow.GetAddress[Arrow, Address] with

    override
    def f(
      arrow: Arrow
    ): Address =

      arrow.address

  given general.arrow.GetEntry[Arrow, Entry] with

    override
    def f(
      arrow: Arrow
    ): Entry =

      arrow.entry

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

  given general.arrow.HasSourceDot[Arrow] with

    override
    def f(
      arrow: Arrow
    ): Boolean =

      !arrow.entry.address1.isZero

  given general.arrow.HasTargetDot[Arrow] with

    override
    def f(
      arrow: Arrow
    ): Boolean =

      !arrow.entry.address4.isZero

  given [NETWORK](using
    general.network.UpdateArrow[NETWORK, Arrow]
  ): general.arrow.SetNextSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      nextSourceArrow: Arrow,
      network: NETWORK
    ): Either[String, NETWORK] =

      val modifiedEntry = arrow.entry.copy(address3 = arrow.address)
      val modifiedArrow = arrow.copy(entry = modifiedEntry)

      for {
        modifiedNetwork <- network.updateArrow(modifiedArrow)
      } yield modifiedNetwork

