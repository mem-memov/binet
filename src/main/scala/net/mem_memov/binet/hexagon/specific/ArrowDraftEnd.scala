package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class ArrowDraftEnd(
  sourceDotReference: DotReference,
  previousSourceArrowReference: ArrowReference,
  targetDotReference: DotReference,
  previousTargetArrowReference: ArrowReference
)

object ArrowDraftEnd:

  given [ADDRESS, ARROW, DICTIONARY, ENTRY, FACTORY](using
    general.dictionary.Append[DICTIONARY, ADDRESS, ENTRY],
    general.factory.MakeArrow[FACTORY, ARROW, ENTRY],
    general.dotReference.GetAddress[DotReference, ADDRESS],
    general.arrowReference.GetAddressOption[ArrowReference, ADDRESS]
  )(using
    factory: FACTORY
  ): general.arrowDraftEnd.CreateArrow[ArrowDraftEnd, ARROW, DICTIONARY] with

    override
    def f(
      arrowDraftEnd: ArrowDraftEnd,
      dictionary: DICTIONARY
    ): Either[String, (DICTIONARY, ARROW)] =

      val sourceDotAddress = arrowDraftEnd.sourceDotReference.getAddress
      val previousSourceArrowAddressOption = arrowDraftEnd.previousSourceArrowReference.getAddressOption
      val targetDotAddress = arrowDraftEnd.targetDotReference.getAddress
      val previousTargetArrowAddressOption = arrowDraftEnd.previousTargetArrowReference.getAddressOption

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


