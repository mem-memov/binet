package net.mem_memov.binet.hexagon.general.dot

trait GetSourceArrowAddress[DOT, ADDRESS]:

  def f(
    dot: DOT
  ): Option[ADDRESS]

  extension (dot: DOT)

    def getSourceArrowAddress: Option[ADDRESS] =

      f(dot)
