package net.mem_memov.binet.hexagon.general.dot

trait GetAddress[DOT, ADDRESS]:

   def f(
     dot: DOT
   ): ADDRESS

   extension (dot: DOT)

     def getAddress: ADDRESS =

       f(dot)
