package net.mem_memov.binet.hexagon.general.dot

trait ToSource[DOT, SOURCE]:
  
  def f(
    dot: DOT
  ): SOURCE
  
  extension (dot: DOT)
    
    def toSource: SOURCE =

      f(dot)
