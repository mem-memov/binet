package net.mem_memov.binet.hexagon.general.source

trait IsSmallerThanTarget[SOURCE, TARGET]:

  def f(
    source: SOURCE,
    target: TARGET
  ): Boolean

  extension (source: SOURCE)

    def isSmallerThanTarget(
      target: TARGET
    ): Boolean =

      f(source, target)
