package net.mem_memov.binet.hexagon.general.arrow

trait ToHead[ARROW, HEAD]:
  
  def f(
    arrow: ARROW
  ): HEAD
  
  extension (arrow: ARROW)
    
    def toHead: HEAD =

      f(arrow)
