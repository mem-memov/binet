package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class ArrowDraftEnd(
  sourceDotReference: DotReference,
  previousSourceArrowReference: ArrowReference,
  targetDotReference: DotReference,
  previousTargetArrowReference: ArrowReference
)

object ArrowDraftEnd:

  given [ADDRESS, ARROW_REFERENCE, ARROW, DICTIONARY, DOT_REFERENCE, ENTRY, FACTORY](using // TODO: remove cyclic references
    => general.dictionary.Append[DICTIONARY, ADDRESS, ENTRY],
    => general.factory.MakeArrow[FACTORY, ARROW, ENTRY],
    => general.dotReference.GetAddress[DOT_REFERENCE, ADDRESS],
    => general.arrowReference.GetAddressOption[ARROW_REFERENCE, ADDRESS]
  )(using
    factory: FACTORY
  ): general.arrowDraftEnd.CreateArrow[ArrowDraftEnd, ARROW, DICTIONARY] with

    override
    def f(
      arrowDraftEnd: ArrowDraftEnd,
      dictionary: DICTIONARY
    ): Either[String, (DICTIONARY, ARROW)] =

      val sourceDotAddress = arrowDraftEnd.sourceDotReference.getAddress
      val previousSourceArrowAddressOption: Option[ADDRESS] = arrowDraftEnd.previousSourceArrowReference.getAddressOption
      val targetDotAddress: ADDRESS = arrowDraftEnd.targetDotReference.getAddress
      val previousTargetArrowAddressOption: Option[ADDRESS] = arrowDraftEnd.previousTargetArrowReference.getAddressOption

      val addressOptions = (
        Option(sourceDotAddress),
        previousSourceArrowAddressOption,
        Option.empty[ADDRESS],
        Option(targetDotAddress),
        previousTargetArrowAddressOption,
        Option.empty[ADDRESS]
      )
      
      for {
        appendResult <- dictionary.append(addressOptions)
        (modifiedDictionary, entries) = appendResult
      } yield
        val arrow = factory.makeArrow(entries)
        (modifiedDictionary, arrow)


