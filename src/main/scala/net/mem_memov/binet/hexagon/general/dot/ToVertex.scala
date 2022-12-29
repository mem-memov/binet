package net.mem_memov.binet.hexagon.general.dot

trait ToVertex[DOT, VERTEX]:
  
  def f(
    dot: DOT
  ): VERTEX
  
  extension (dot: DOT)
    
    def toVertex: VERTEX =

      f(dot)
