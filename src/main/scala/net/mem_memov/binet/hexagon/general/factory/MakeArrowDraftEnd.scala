package net.mem_memov.binet.hexagon.general.factory

trait MakeArrowDraftEnd[FACTORY, ARROW_DRAFT_END, ARROW_REFERENCE, DOT_REFERENCE]:

  def f(
    sourceDotReference: DOT_REFERENCE,
    previousSourceArrowReference: ARROW_REFERENCE,
    targetDotReference: DOT_REFERENCE,
    previousTargetArrowReference: ARROW_REFERENCE
  ): ARROW_DRAFT_END

  extension (factory: FACTORY)

    def makeArrowDraftEnd(
      sourceDotReference: DOT_REFERENCE,
      previousSourceArrowReference: ARROW_REFERENCE,
      targetDotReference: DOT_REFERENCE,
      previousTargetArrowReference: ARROW_REFERENCE
    ): ARROW_DRAFT_END =

      f(sourceDotReference, previousSourceArrowReference, targetDotReference, previousTargetArrowReference)
