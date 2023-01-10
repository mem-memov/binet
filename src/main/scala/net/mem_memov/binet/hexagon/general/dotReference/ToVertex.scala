package net.mem_memov.binet.hexagon.general.dotReference

trait ToVertex[DOT_REFERENCE, VERTEX]:

   def f(
     dotReference: DOT_REFERENCE
   ): VERTEX

   extension (dotReference: DOT_REFERENCE)

     def toVertex: VERTEX =

       f(dotReference)
