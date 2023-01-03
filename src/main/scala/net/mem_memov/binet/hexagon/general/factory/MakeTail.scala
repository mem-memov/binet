package net.mem_memov.binet.hexagon.general.factory

trait MakeTail[FACTORY, ARROW_REFERENCE, DOT_REFERENCE, TAIL]:

  def f(
    tailDotReference: DOT_REFERENCE,
    previousTailArrowReference: ARROW_REFERENCE,
    nextTailArrowReference: ARROW_REFERENCE,
    headDotReference: DOT_REFERENCE,
    previousHeadArrowReference: ARROW_REFERENCE,
    nextHeadArrowReference: ARROW_REFERENCE
  ): TAIL

  extension (factory: FACTORY)

    def makeTail(
      tailDotReference: DOT_REFERENCE,
      previousTailArrowReference: ARROW_REFERENCE,
      nextTailArrowReference: ARROW_REFERENCE,
      headDotReference: DOT_REFERENCE,
      previousHeadArrowReference: ARROW_REFERENCE,
      nextHeadArrowReference: ARROW_REFERENCE
    ): TAIL =

      f(
        tailDotReference,
        previousTailArrowReference,
        nextTailArrowReference,
        headDotReference,
        previousHeadArrowReference,
        nextHeadArrowReference
      )
