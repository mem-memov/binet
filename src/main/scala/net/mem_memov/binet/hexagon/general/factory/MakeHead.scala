package net.mem_memov.binet.hexagon.general.factory

trait MakeHead[FACTORY, ARROW_REFERENCE, DOT_REFERENCE, HEAD]:

  def f(
    tailDotReference: DOT_REFERENCE,
    previousTailArrowReference: ARROW_REFERENCE,
    nextTailArrowReference: ARROW_REFERENCE,
    headDotReference: DOT_REFERENCE,
    previousHeadArrowReference: ARROW_REFERENCE,
    nextHeadArrowReference: ARROW_REFERENCE
  ): HEAD

  extension (factory: FACTORY)

    def makeHead(
      tailDotReference: DOT_REFERENCE,
      previousTailArrowReference: ARROW_REFERENCE,
      nextTailArrowReference: ARROW_REFERENCE,
      headDotReference: DOT_REFERENCE,
      previousHeadArrowReference: ARROW_REFERENCE,
      nextHeadArrowReference: ARROW_REFERENCE
    ): HEAD =

      f(
        tailDotReference,
        previousTailArrowReference,
        nextTailArrowReference,
        headDotReference,
        previousHeadArrowReference,
        nextHeadArrowReference
      )
