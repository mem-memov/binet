package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class ArrowReference(
  entry: Entry
)

object ArrowReference:

  given [ARROW, DICTIONARY, FACTORY](using
    general.dictionary.Read[Dictionary, Entry],
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
            val arrow = factory.makeArrow(entries)
            if arrow.isArrow then
              Right(arrow)
            else
              Left("Not an arrow")
        } yield Some(arrow)
