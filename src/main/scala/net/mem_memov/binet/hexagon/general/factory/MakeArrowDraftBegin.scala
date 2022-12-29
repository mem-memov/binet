package net.mem_memov.binet.hexagon.general.factory

trait MakeArrowDraftBegin[FACTORY, ARROW_DRAFT_BEGIN, ARROW_REFERENCE, DOT_REFERENCE]:

   def f(
     sourceDotReference: DOT_REFERENCE,
     previousSourceArrowReference: ARROW_REFERENCE
   ): ARROW_DRAFT_BEGIN

   extension (factory: FACTORY)

     def makeArrowDraftBegin(
       sourceDotReference: DOT_REFERENCE,
       previousSourceArrowReference: ARROW_REFERENCE
     ): ARROW_DRAFT_BEGIN =

       f(sourceDotReference, previousSourceArrowReference)
