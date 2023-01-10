package net.mem_memov.binet.hexagon.general.factory

trait MakeSuccessor[FACTORY, ARROW_REFERENCE, COUNTER, DOT_REFERENCE, SUCCESSOR]:

  def f(
    dotReference: DOT_REFERENCE,
    nextDotReference: DOT_REFERENCE,
    sourceCounter: COUNTER,
    targetCounter: COUNTER,
    sourceArrowReference: ARROW_REFERENCE,
    targetArrowReference: ARROW_REFERENCE
  ): SUCCESSOR

  extension (factory: FACTORY)

    def makeSuccessor(
      dotReference: DOT_REFERENCE,
      nextDotReference: DOT_REFERENCE,
      sourceCounter: COUNTER,
      targetCounter: COUNTER,
      sourceArrowReference: ARROW_REFERENCE,
      targetArrowReference: ARROW_REFERENCE
    ): SUCCESSOR =

      f(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )
