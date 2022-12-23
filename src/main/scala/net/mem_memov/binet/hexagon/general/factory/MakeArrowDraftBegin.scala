package net.mem_memov.binet.hexagon.general.factory

trait MakeArrowDraftBegin[FACTORY, ARROW_DRAFT_BEGIN, ARROW_REFERENCE, DOT_IDENTIFIER]:

   def f(
     sourceDotIdentifier: DOT_IDENTIFIER,
     previousSourceArrow: ARROW_REFERENCE
   ): ARROW_DRAFT_BEGIN

   extension (factory: FACTORY)

     def makeArrowDraftBegin(
       sourceDotIdentifier: DOT_IDENTIFIER,
       previousSourceArrow: ARROW_REFERENCE
     ): ARROW_DRAFT_BEGIN =

       f(sourceDotIdentifier, previousSourceArrow)
