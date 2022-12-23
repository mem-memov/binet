package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Network(
  dictionary: Dictionary
)

object Network:

  given [ARROW, ARROW_DRAFT_END, ENTRY, FACTORY](using
    general.arrowDraftEnd.CreateArrow[ARROW_DRAFT_END, ARROW, Dictionary]
  ): general.network.CreateArrow[Network, ARROW] with

    override
    def f(
      network: Network,
      arrowDraftEnd: ARROW_DRAFT_END
    ): Either[String, (Network, ARROW)] =

      for {
        result <- arrowDraftEnd.createArrow(network.dictionary)
        (modifiedDictionary, arrow) = result
      } yield
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, arrow)

  given [ADDRESS, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ADDRESS, ENTRY],
    general.dictionary.GetNextAddress[Dictionary, ADDRESS],
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.factory.MakeDot[FACTORY, Dot, ENTRY],
    general.entry.SetAddress1[ENTRY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.network.CreateDot[Network, Dot] with

    override
    def f(
      network: Network
    ): Either[String, (Network, Dot)] =

      val dotIdentifierAddress = network.dictionary.getNextAddress

      for {
        appendResult <- network.dictionary.append((Some(dotIdentifierAddress), None, None, None, None, None))
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
    ): Either[String, ARROW] =

      arrowReference.readArrow(network.dictionary)

  given [DOT_REFERENCE, DOT](using
    general.dotReference.ReadDot[DOT_REFERENCE, Dictionary, DOT]
  ): general.network.ReadDot[Network, DOT_REFERENCE, DOT] with

    override
    def f(
      network: Network,
      dotReference: DOT_REFERENCE
    ): Either[String, DOT] =

      dotReference.readDot(network.dictionary)

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
      } yield network.copy(dictionary = modifiedNeDictionary)

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
      } yield network.copy(dictionary = modifiedNeDictionary)

