package net.mem_memov.binet.hexagon.general.target

trait HasMoreSources[TARGET, DOT]:

  def f(
    target: TARGET,
    sourceDot: DOT
  ): Boolean

  extension (target: TARGET)

    def hasMoreSources(
      sourceDot: DOT
    ): Boolean =

      f(target, sourceDot)
