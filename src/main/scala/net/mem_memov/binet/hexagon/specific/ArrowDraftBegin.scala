package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.hexagon.general

case class ArrowDraftBegin(
  sourceDotIdentifier: DotIdentifier,
  previousSourceArrow: ArrowReference
)

object ArrowDraftBegin:
  
  given [ARROW_DRAFT_END, FACTORY](using
    general.factory.MakeArrowDraftEnd[FACTORY, ARROW_DRAFT_END, ArrowReference, DotIdentifier]
  )(using
    factory: FACTORY
  ): general.arrowDraftBegin.ToEnd[ArrowDraftBegin, ARROW_DRAFT_END, ArrowReference, DotIdentifier] with

    override 
    def f(
      arrowDraftBegin: ArrowDraftBegin, 
      targetDotIdentifier: DotIdentifier,
      previousTargetArrow: ArrowReference
    ): ARROW_DRAFT_END =

      factory.makeArrowDraftEnd(arrowDraftBegin.sourceDotIdentifier, arrowDraftBegin.previousSourceArrow, targetDotIdentifier, previousTargetArrow)
      
