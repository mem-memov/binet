package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class DotReference(
  entry: Entry
)

object DotReference:

  given [ADDRESS, DICTIONARY, DOT](using
    general.dictionary.Read[DICTIONARY, ADDRESS, Entry],
    general.entry.GetContent[Entry, ADDRESS],
    general.factory.MakeDot[FACTORY, DOT, Entry],
    general.dot.IsDot[Dot]
  )(using
    factory: FACTORY
  ): general.dotReference.ReadDot[DotReference, DICTIONARY, DOT] with

    override
    def f(
      dotReference: DotReference,
      dictionary: DICTIONARY
    ): Either[String, DOT] =

      for {
        entries <- dictionary.read(dotReference.entry.getContent)
        dot <-
          val dot = factory.makeDot(entries)
          if dot.isDot then
            Right(dot)
          else
            Left("Not a dot")
      } yield dot

