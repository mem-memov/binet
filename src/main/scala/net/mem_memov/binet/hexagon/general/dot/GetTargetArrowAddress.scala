package net.mem_memov.binet.hexagon.general.dot

trait GetTargetArrowAddress[DOT, ADDRESS]:

  def f(
    dot: DOT
  ): Option[ADDRESS]

  extension (dot: DOT)

    def getTargetArrowAddress: Option[ADDRESS] =

      f(dot)
