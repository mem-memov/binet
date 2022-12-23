package net.mem_memov.binet.hexagon.general.arrowDraftBegin

trait ToEnd[ARROW_DRAFT_BEGIN, ARROW_DRAFT_END, ARROW_REFERENCE, DOT_IDENTIFIER]:

  def f(
    arrowDraftBegin: ARROW_DRAFT_BEGIN,
    targetDotIdentifier: DOT_IDENTIFIER,
    previousTargetArrow: ARROW_REFERENCE
  ): ARROW_DRAFT_END

  extension (arrowDraftBegin: ARROW_DRAFT_BEGIN)

    def toEnd(
      targetDotIdentifier: DOT_IDENTIFIER,
      previousTargetArrow: ARROW_REFERENCE
    ): ARROW_DRAFT_END =

      f(arrowDraftBegin, targetDotIdentifier, previousTargetArrow)
