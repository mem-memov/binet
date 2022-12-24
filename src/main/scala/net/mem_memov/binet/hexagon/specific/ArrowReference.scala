package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class ArrowReference(
  entry: Entry
)

object ArrowReference:

  given [ADDRESS, ARROW, DICTIONARY, FACTORY](using
    general.dictionary.Read[Dictionary, ADDRESS, Entry],
    general.factory.MakeArrow[FACTORY, ARROW, Entry],
    general.arrow.IsArrow[ARROW],
    general.entry.GetContent[Entry, ADDRESS],
  )(using
    factory: FACTORY
  ): general.arrowReference.ReadArrow[ArrowReference, ARROW, DICTIONARY] with

    override
    def f(
      arrowReference: ArrowReference,
      dictionary: DICTIONARY
    ): Either[String, ARROW] =

      for {
        entries <- dictionary.read(arrowReference.entry.getContent)
        arrow <-
          val arrow = factory.makeArrow(entries)
          if arrow.isArrow then
            Right(arrow)
          else
            Left("Not an arrow")
      } yield arrow
