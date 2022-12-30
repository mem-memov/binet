package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.hexagon.general

case class ArrowDraftBegin(
  sourceDotReference: DotReference,
  previousSourceArrow: ArrowReference
)

object ArrowDraftBegin:
  
  given [ARROW_DRAFT_END, FACTORY](using
    general.factory.MakeArrowDraftEnd[FACTORY, ARROW_DRAFT_END, ArrowReference, DotReference]
  )(using
    factory: FACTORY
  ): general.arrowDraftBegin.ToEnd[ArrowDraftBegin, ARROW_DRAFT_END, ArrowReference, DotReference] with

    override 
    def f(
      arrowDraftBegin: ArrowDraftBegin, 
      targetDotReference: DotReference,
      previousTargetArrow: ArrowReference
    ): ARROW_DRAFT_END =

      factory.makeArrowDraftEnd(arrowDraftBegin.sourceDotReference, arrowDraftBegin.previousSourceArrow, targetDotReference, previousTargetArrow)
      
