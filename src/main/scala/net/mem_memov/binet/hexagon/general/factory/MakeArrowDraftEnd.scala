package net.mem_memov.binet.hexagon.general.factory

trait MakeArrowDraftEnd[FACTORY, ARROW_DRAFT_END, ARROW_REFERENCE, DOT_IDENTIFIER]:

  def f(
    sourceDotIdentifier: DOT_IDENTIFIER,
    previousSourceArrowReference: ARROW_REFERENCE,
    targetDotIdentifier: DOT_IDENTIFIER,
    previousTargetArrowReference: ARROW_REFERENCE
  ): ARROW_DRAFT_END

  extension (factory: FACTORY)

    def makeArrowDraftBegin(
      sourceDotIdentifier: DOT_IDENTIFIER,
      previousSourceArrowReference: ARROW_REFERENCE,
      targetDotIdentifier: DOT_IDENTIFIER,
      previousTargetArrowReference: ARROW_REFERENCE
    ): ARROW_DRAFT_END =

      f(sourceDotIdentifier, previousSourceArrowReference, targetDotIdentifier, previousTargetArrowReference)
