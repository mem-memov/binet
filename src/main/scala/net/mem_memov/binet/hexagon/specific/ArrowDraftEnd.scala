package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class ArrowDraftEnd(
  sourceDotIdentifier: DotIdentifier,
  previousSourceArrowReference: ArrowReference,
  targetDotIdentifier: DotIdentifier,
  previousTargetArrowReference: ArrowReference
)

object ArrowDraftEnd:
  
  given [ADDRESS, ARROW_REFERENCE, ARROW, DICTIONARY, DOT_IDENTIFIER, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ENTRY],
    general.factory.MakeArrow[FACTORY, ARROW, ENTRY],
    general.dotIdentifier.GetAddress[DOT_IDENTIFIER, ADDRESS],
    general.arrowReference.GetAddressOption[ARROW_REFERENCE, ADDRESS]
  )(using
    factory: FACTORY
  ): general.arrowDraftEnd.CreateArrow[ArrowDraftEnd, ARROW, DICTIONARY] with

    override 
    def f(
      arrowDraftEnd: ArrowDraftEnd, 
      dictionary: DICTIONARY
    ): Either[String, (DICTIONARY, ARROW)] =

      val addressOptions = (
        Some(arrowDraftEnd.sourceDotIdentifier.getAddress),
        arrowDraftEnd.previousSourceArrowReference.getAddressOption,
        None,
        Some(arrowDraftEnd.targetDotIdentifier.getAddress),
        arrowDraftEnd.previousTargetArrowReference.getAddressOption,
        None
      )
      
      for {
        appendResult <- dictionary.append(addressOptions)
        (modifiedDictionary, entries) = appendResult
      } yield
        val arrow = factory.makeArrow(entries)
        (modifiedDictionary, arrow)


