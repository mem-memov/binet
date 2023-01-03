package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Network(
  dictionary: Dictionary
)

object Network:

  given [ADDRESS, ARROW, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ADDRESS, ENTRY],
    general.factory.MakeArrow[FACTORY, ARROW, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.CreateArrow[Network, ADDRESS, ARROW] with

    override
    def f(
      network: Network,
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      targetDotAddress: ADDRESS,
      targetArrowAddressOption: Option[ADDRESS]
    ): Either[String, (Network, ARROW)] =

      val addressOptions = (
        Option(sourceDotAddress),
        Option.empty[ADDRESS],
        sourceArrowAddressOption,
        Option(targetDotAddress),
        Option.empty[ADDRESS],
        targetArrowAddressOption
      )

      for {
        appendResult <- network.dictionary.append(addressOptions)
        (modifiedDictionary, entries) = appendResult
      } yield
        val arrow = factory.makeArrow(entries)
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, arrow)

  given [ENTRY, FACTORY](using
    general.dictionary.AppendDot[Dictionary, ENTRY],
    general.factory.MakeDot[FACTORY, Dot, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.CreateDot[Network, Dot] with

    override
    def f(
      network: Network
    ): Either[String, (Network, Dot)] =

      for {
        appendResult <- network.dictionary.appendDot
        (modifiedDictionary, entries) = appendResult
      } yield
        val dot = factory.makeDot(entries)
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, dot)

  given [ARROW, ARROW_REFERENCE](using
    general.arrowReference.ReadArrow[ARROW_REFERENCE, ARROW, Dictionary]
  ): general.network.ReadArrow[Network, ARROW, ARROW_REFERENCE] with

    override
    def f(
      network: Network,
      arrowReference: ARROW_REFERENCE
    ): Either[String, Option[ARROW]] =

      arrowReference.readArrow(network.dictionary)

  given [DOT_REFERENCE, DOT](using
    general.dotReference.ReadDot[DOT_REFERENCE, Dictionary, DOT]
  ): general.network.ReadDot[Network, DOT, DOT_REFERENCE] with

    override
    def f(
      network: Network,
      dotReference: DOT_REFERENCE
    ): Either[String, DOT] =

      dotReference.readDot(network.dictionary)

  given [ENTRY](using
    general.dictionary.Update[Dictionary, ENTRY],
    general.entry.Save[ENTRY, Dictionary]
  ): general.network.UpdateEntry[Network, ENTRY] with

    override
    def f(
      network: Network,
      entry: ENTRY
    ): Either[String, Network] =

      for {
        modifiedNeDictionary <- entry.save(network.dictionary)
      } yield network.copy(dictionary = modifiedNeDictionary)


