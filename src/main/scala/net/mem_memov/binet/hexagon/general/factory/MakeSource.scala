package net.mem_memov.binet.hexagon.general.factory

trait MakeSource[FACTORY, ARROW_REFERENCE, COUNTER, DOT_REFERENCE, SOURCE]:

  def f(
    dotReference: DOT_REFERENCE,
    nextDotReference: DOT_REFERENCE,
    sourceCounter: COUNTER,
    targetCounter: COUNTER,
    sourceArrowReference: ARROW_REFERENCE,
    targetArrowReference: ARROW_REFERENCE
  ): SOURCE
  
  extension (factory: FACTORY)
    
    def makeSource(
      dotReference: DOT_REFERENCE,
      nextDotReference: DOT_REFERENCE,
      sourceCounter: COUNTER,
      targetCounter: COUNTER,
      sourceArrowReference: ARROW_REFERENCE,
      targetArrowReference: ARROW_REFERENCE
    ): SOURCE =

      f(
        dotReference,
        nextDotReference,
        sourceCounter,
        targetCounter,
        sourceArrowReference,
        targetArrowReference
      )
