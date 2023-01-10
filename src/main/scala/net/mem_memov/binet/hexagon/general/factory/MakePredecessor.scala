package net.mem_memov.binet.hexagon.general.factory

trait MakePredecessor[FACTORY, ARROW_REFERENCE, COUNTER, DOT_REFERENCE, PREDECESSOR]:

  def f(
    dotReference: DOT_REFERENCE,
    nextDotReference: DOT_REFERENCE,
    sourceCounter: COUNTER,
    targetCounter: COUNTER,
    sourceArrowReference: ARROW_REFERENCE,
    targetArrowReference: ARROW_REFERENCE
  ): PREDECESSOR

  extension (factory: FACTORY)

    def makePredecessor(
      dotReference: DOT_REFERENCE,
      nextDotReference: DOT_REFERENCE,
      sourceCounter: COUNTER,
      targetCounter: COUNTER,
      sourceArrowReference: ARROW_REFERENCE,
      targetArrowReference: ARROW_REFERENCE
    ): PREDECESSOR =

      f(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )
