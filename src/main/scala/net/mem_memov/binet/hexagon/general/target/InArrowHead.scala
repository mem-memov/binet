package net.mem_memov.binet.hexagon.general.target

trait InArrowHead[TARGET, ARROW]:

  def f(
    target: TARGET,
    arrow: ARROW
  ): Boolean

  extension (target: TARGET)

    def inArrowHead(
      arrow: ARROW
    ): Boolean =

      f(target, arrow)
