package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class ArrowReference(
  entry: Entry
)

object ArrowReference:

  given [ADDRESS](using
    general.entry.GetContent[Entry, ADDRESS],
    general.entry.IsContentEmpty[Entry]
  ): general.arrowReference.GetAddressOption[ArrowReference, ADDRESS] with

    override
    def f(
      arrowReference: ArrowReference
    ): Option[ADDRESS] =

      if arrowReference.entry.isContentEmpty then
        None
      else
        Some(arrowReference.entry.getContent)



  given [ARROW, DICTIONARY, FACTORY](using
    general.dictionary.Read[DICTIONARY, Entry],
    general.factory.MakeArrow[FACTORY, ARROW, Entry],
    general.arrow.IsArrow[ARROW],
    general.entry.IsContentEmpty[Entry]
  )(using
    factory: FACTORY
  ): general.arrowReference.ReadArrow[ArrowReference, ARROW, DICTIONARY] with

    override
    def f(
      arrowReference: ArrowReference,
      dictionary: DICTIONARY
    ): Either[String, Option[ARROW]] =

      if arrowReference.entry.isContentEmpty then
        Right(None)
      else
        for {
          entries <- dictionary.read(arrowReference.entry)
          arrow <-
            val newArrow = factory.makeArrow(entries)
            if newArrow.isArrow then
              Right(newArrow)
            else
              Left("Not an arrow")
        } yield Some(arrow)

  given [ARROW, HEAD, NETWORK](using
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.arrow.ToHead[ARROW, HEAD]
  ): general.arrowReference.ReadHead[ArrowReference, HEAD, NETWORK] with

    override
    def f(
      arrowReference: ArrowReference,
      network: NETWORK
    ): Either[String, Option[HEAD]] =

      for {
        optionArrow <- network.readArrow(arrowReference)
      } yield optionArrow.map(_.toHead)

  given [ARROW, TAIL, NETWORK](using
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.arrow.ToTail[ARROW, TAIL]
  ): general.arrowReference.ReadTail[ArrowReference, NETWORK, TAIL] with

    override
    def f(
      arrowReference: ArrowReference,
      network: NETWORK
    ): Either[String, Option[TAIL]] =

      for {
        optionArrow <- network.readArrow(arrowReference)
      } yield optionArrow.map(_.toTail)

  given [DOT_REFERENCE, NETWORK](using
    general.dotReference.LendPath[DOT_REFERENCE, Entry, NETWORK]
  ): general.arrowReference.ReferencePath[ArrowReference, DOT_REFERENCE, NETWORK] with

    override
    def f(
      arrowReference: ArrowReference,
      dotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, ArrowReference)] =

      for {
        result <- dotReference.lendPath(arrowReference.entry, network)
        (modifiedNetwork, modifiedEntry) = result
      } yield
        val modifiedArrowReference = arrowReference.copy(entry = modifiedEntry)
        (modifiedNetwork, modifiedArrowReference)

