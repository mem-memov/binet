package net.mem_memov.binet.hexagon.general.arrow

trait GetNextSourceArrow[ARROW]:
  
  def f(
    arrow: ARROW
  ): Option[ARROW]
  
  extension (arrow: ARROW)
    
    def getNextSourceArrow: Option[ARROW] =

      f(arrow)
