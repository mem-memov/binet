package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class DotReference(
  entry: Entry
)

object DotReference:

  given [ADDRESS](using
    general.entry.GetContent[Entry, ADDRESS]
  ): general.dotReference.GetAddress[DotReference, ADDRESS] with

    override
    def f(
      dotReference: DotReference
    ): ADDRESS =

      dotReference.entry.getContent

  given (using
    general.entry.ContentEqualsPath[Entry]
  ): general.dotReference.InArrow[DotReference] with

    override
    def f(
      dotReference: DotReference
    ): Boolean =

      !dotReference.entry.contentEqualsPath

  given (using
    general.entry.ContentEqualsPath[Entry]
  ): general.dotReference.InDot[DotReference] with

    override
    def f(
      dotReference: DotReference
    ): Boolean =

      dotReference.entry.contentEqualsPath
  
  given [FACTORY, DICTIONARY, DOT](using
    general.dictionary.Read[DICTIONARY, Entry],
    general.factory.MakeDot[FACTORY, DOT, Entry],
    general.dot.IsDot[DOT]
  )(using
    factory: FACTORY
  ): general.dotReference.ReadDot[DotReference, DICTIONARY, DOT] with

    override
    def f(
      dotReference: DotReference,
      dictionary: DICTIONARY
    ): Either[String, DOT] =

      for {
        entries <- dictionary.read(dotReference.entry)
        dot <-
          val dot = factory.makeDot(entries)
          if dot.isDot then
            Right(dot)
          else
            Left("Not a dot")
      } yield dot

  given (using
    general.entry.SameContent[Entry]
  ): general.dotReference.InSameDirection[DotReference] with

    override
    def f(
      dotReference: DotReference,
      theOther: DotReference
    ): Boolean =

      dotReference.entry.sameContent(theOther.entry)

  given [NETWORK](using
    general.entry.SetContentWithPath[Entry, NETWORK]
  ): general.dotReference.LendPath[DotReference, Entry, NETWORK] with

    override
    def f(
      dotReference: DotReference,
      entry: Entry,
      network: NETWORK
    ): Either[String, (NETWORK, Entry)] =

      entry.setContentWithPath(dotReference.entry, network)

  given [NETWORK](using
    general.dotReference.LendPath[DotReference, Entry, NETWORK]
  ): general.dotReference.ReferencePath[DotReference, NETWORK] with

    override
    def f(
      dotReference: DotReference,
      mentionedDotReference: DotReference,
      network: NETWORK
    ): Either[String, (NETWORK, DotReference)] =

      for {
        result <- mentionedDotReference.lendPath(dotReference.entry, network)
        (modifiedNetwork, modifiedEntry) = result
      } yield
        val modifiedDotReference = dotReference.copy(entry = modifiedEntry)
        (modifiedNetwork, modifiedDotReference)

  given (using
    general.entry.IsContentEmpty[Entry]
  ): general.dotReference.IsEmpty[DotReference] with

    override
    def f(
      dotReference: DotReference
    ): Boolean =

      dotReference.entry.isContentEmpty