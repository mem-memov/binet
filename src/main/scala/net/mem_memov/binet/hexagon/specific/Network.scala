package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

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
  ): general.network.CreateArrow[Network, ADDRESS] with

    override
    def f(
      network: Network,
      sourceAddress: ADDRESS,
      targetAddress: ADDRESS
    ): Either[String, Network] =

      val entry = factory.emptyEntry().setAddress1(sourceAddress).setAddress3(targetAddress)

      for {
        modifiedDictionary <- network.dictionary.append(entry)
        address <- modifiedDictionary.getAddress
      } yield
        val arrow = factory.makeArrow(address, entry)
        Network(None, Some(arrow), modifiedDictionary)

  given [ADDRESS, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ENTRY],
    general.dictionary.GetAddress[Dictionary, ADDRESS],
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.factory.MakeDot[FACTORY, ADDRESS, Dot, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.CreateDot[Network] with

    override
    def f(network: Network): Either[String, Network] =

      val entry = factory.emptyEntry()

      for {
        modifiedDictionary <- network.dictionary.append(entry)
        address <- modifiedDictionary.getAddress
      } yield
        val dot = factory.makeDot(address, entry)
        Network(Some(dot), None, modifiedDictionary)

  given [ENTRY, FACTORY](using
    general.dictionary.Read[Dictionary, Address, ENTRY],
    general.factory.MakeArrow[FACTORY, Address, Arrow, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.ReadArrow[Network, Address] with

    override
    def f(
      network: Network,
      address: Address
    ): Either[String, Network] =

      for {
        entry <- network.dictionary.read(address)
      } yield
        val arrow = factory.makeArrow(address, entry)
        Network(None, Some(arrow), network.dictionary)

  given [ENTRY, FACTORY](using
    general.dictionary.Read[Dictionary, Address, ENTRY],
    general.factory.MakeDot[FACTORY, Address, Dot, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.ReadDot[Network, Address] with

    override
    def f(
      network: Network,
      address: Address
    ): Either[String, Network] =

      for {
        entry <- network.dictionary.read(address)
      } yield
        val dot = factory.makeDot(address, entry)
        Network(Some(dot), None, network.dictionary)

  given general.network.GetArrow[Network, Arrow] with

    override
    def f(network: Network): Either[String, Arrow] =

      network.optionArrow match
        case Some(arrow) => Right(arrow)
        case None => Left("No arrow")

  given general.network.GetDot[Network, Dot] with

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