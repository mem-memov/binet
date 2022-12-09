package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Network(
  optionDot: Option[Dot],
  optionArrow: Option[Arrow],
  dictionary: Dictionary
)

object Network:

  given [ADDRESS, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ENTRY],
    general.dictionary.GetAddress[Dictionary, ADDRESS],
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.factory.MakeArrow[FACTORY, ADDRESS, Arrow, ENTRY],
    general.entry.SetAddress1[ENTRY, ADDRESS],
    general.entry.SetAddress3[ENTRY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.network.CreateArrow[Network, ADDRESS, Arrow] with

    override
    def f(
      network: Network,
      sourceAddress: ADDRESS,
      targetAddress: ADDRESS
    ): Either[String, (Network, Arrow)] =

      val entry = factory.emptyEntry().setAddress1(sourceAddress).setAddress3(targetAddress)

      for {
        modifiedDictionary <- network.dictionary.append(entry)
        address <- modifiedDictionary.getAddress
      } yield
        val arrow = factory.makeArrow(address, entry)
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, arrow)

  given [ADDRESS, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ENTRY],
    general.dictionary.GetAddress[Dictionary, ADDRESS],
    general.dictionary.GetNextAddress[Dictionary, ADDRESS],
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.factory.MakeDot[FACTORY, ADDRESS, Dot, ENTRY],
    general.entry.SetAddress1[ENTRY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.network.CreateDot[Network, Dot] with

    override
    def f(
      network: Network
    ): Either[String, (Network, Dot)] =

      val entry = factory.emptyEntry()
        .setAddress1(network.dictionary.getNextAddress)

      for {
        modifiedDictionary <- network.dictionary.append(entry)
        address <- modifiedDictionary.getAddress
      } yield
        val dot = factory.makeDot(address, entry)
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, dot)

  given [ENTRY, FACTORY](using
    general.dictionary.Read[Dictionary, Address, ENTRY],
    general.factory.MakeArrow[FACTORY, Address, Arrow, ENTRY],
    memory.general.address.IsZero[Address]
  )(using
    factory: FACTORY
  ): general.network.ReadArrow[Network, Address, Arrow] with

    override
    def f(
      network: Network,
      address: Address
    ): Either[String, Arrow] =

      for {
        entry <- network.dictionary.read(address)
      } yield factory.makeArrow(address, entry)

  given [ENTRY, FACTORY](using
    general.dictionary.Read[Dictionary, Address, ENTRY],
    general.factory.MakeDot[FACTORY, Address, Dot, ENTRY],
    memory.general.address.IsZero[Address]
  )(using
    factory: FACTORY
  ): general.network.ReadDot[Network, Address, Dot] with

    override
    def f(
      network: Network,
      address: Address
    ): Either[String, Dot] =

      for {
        entry <- network.dictionary.read(address)
      } yield factory.makeDot(address, entry)

  given general.network.RequireArrow[Network, Arrow] with

    override
    def f(network: Network): Either[String, Arrow] =

      network.optionArrow match
        case Some(arrow) => Right(arrow)
        case None => Left("No arrow")

  given general.network.RequireDot[Network, Dot] with

    override
    def f(network: Network): Either[String, Dot] =

      network.optionDot match
        case Some(dot) => Right(dot)
        case None => Left("No dot")

  given [ADDRESS, ENTRY](using
    general.dictionary.Update[Dictionary, ADDRESS, ENTRY],
    general.arrow.GetAddress[Arrow, ADDRESS],
    general.arrow.GetEntry[Arrow, ENTRY]
  ): general.network.UpdateArrow[Network, Arrow] with

    override
    def f(
      network: Network,
      arrow: Arrow
    ): Either[String, Network] =

      for {
        modifiedNeDictionary <- network.dictionary.update(arrow.getAddress, arrow.getEntry)
      } yield Network(None, Some(arrow), network.dictionary)

  given [ADDRESS, ENTRY](using
    general.dictionary.Update[Dictionary, ADDRESS, ENTRY],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.GetEntry[Dot, ENTRY],
  ): general.network.UpdateDot[Network, Dot] with

    override
    def f(
      network: Network,
      dot: Dot
    ): Either[String, Network] =

      for {
        modifiedNeDictionary <- network.dictionary.update(dot.getAddress, dot.getEntry)
      } yield Network(Some(dot), None, network.dictionary)

  given general.network.TakeState[Network] with

    override
    def f(
      network: Network,
      donor: Network
    ): Network =

      network.copy(dictionary = donor.dictionary)

  given general.network.HasArrow[Network] with

    override
    def f(
      network: Network
    ): Boolean =

      network.optionArrow.isDefined

  given general.network.HasDot[Network] with

    override
    def f(
      network: Network
    ): Boolean =

      network.optionDot.isDefined