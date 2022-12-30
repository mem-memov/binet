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
