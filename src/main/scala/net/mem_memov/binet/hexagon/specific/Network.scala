package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

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
    general.factory.MakeArrow[FACTORY, ADDRESS, Arrow, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.CreateArrow[Network, Arrow] with

    override
    def f(network: Network): Either[String, Network] =

      val entry = factory.emptyEntry()

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
