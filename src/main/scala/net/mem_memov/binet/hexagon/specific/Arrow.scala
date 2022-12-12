package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  address: Address,
  entry: Entry
)

object Arrow:

  given (using
    general.entry.GetAddress1[Entry, Address]
  ): general.arrow.IsArrow[Arrow] with

    override def f(arrow: Arrow): Boolean =

      arrow.address != arrow.entry.address1

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
    general.network.ReadDot[NETWORK, Address, DOT]
  ): general.arrow.GetSourceDot[Arrow, DOT, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, DOT] =

      network.readDot(arrow.entry.address1)

  given [FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, Arrow, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.arrow.GetPreviousSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      fetcher.fetchArrow(arrow.entry.address2, network)

  given [FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, Arrow, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.arrow.GetNextSourceArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      fetcher.fetchArrow(arrow.entry.address3, network)

  given [DOT, NETWORK](using
    general.network.ReadDot[NETWORK, Address, DOT]
  ): general.arrow.GetTargetDot[Arrow, DOT, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, DOT] =

      network.readDot(arrow.entry.address4)

  given [FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, Arrow, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.arrow.GetPreviousTargetArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      fetcher.fetchArrow(arrow.entry.address5, network)

  given [FETCHER, NETWORK](using
    specific.common.general.fetcher.FetchArrow[FETCHER, Address, Arrow, NETWORK]
  )(using
    fetcher: FETCHER
  ): general.arrow.GetNextTargetArrow[Arrow, NETWORK] with

    override
    def f(
      arrow: Arrow,
      network: NETWORK
    ): Either[String, Option[Arrow]] =

      fetcher.fetchArrow(arrow.entry.address6, network)

  given general.arrow.HasSourceDot[Arrow, Address] with

    override
    def f(
      arrow: Arrow,
      sourceDotAddress: Address
    ): Boolean =

      arrow.entry.address1 == sourceDotAddress

  given general.arrow.HasTargetDot[Arrow, Address] with

    override
    def f(
      arrow: Arrow,
      targetDotAddress: Address
    ): Boolean =

      arrow.entry.address4 == targetDotAddress

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

