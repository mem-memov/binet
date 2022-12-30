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