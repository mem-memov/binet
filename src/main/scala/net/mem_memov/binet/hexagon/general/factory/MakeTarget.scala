package net.mem_memov.binet.hexagon.general.factory

trait MakeTarget[FACTORY, ARROW_REFERENCE, COUNTER, DOT_REFERENCE, TARGET]:

  def f(
    dotReference: DOT_REFERENCE,
    nextDotReference: DOT_REFERENCE,
    sourceCounter: COUNTER,
    targetCounter: COUNTER,
    sourceArrowReference: ARROW_REFERENCE,
    targetArrowReference: ARROW_REFERENCE
  ): TARGET

  extension (factory: FACTORY)

    def makeTarget(
      dotReference: DOT_REFERENCE,
      nextDotReference: DOT_REFERENCE,
      sourceCounter: COUNTER,
      targetCounter: COUNTER,
      sourceArrowReference: ARROW_REFERENCE,
      targetArrowReference: ARROW_REFERENCE
    ): TARGET =

      f(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )
