package net.mem_memov.binet.hexagon.general.target

trait CountSources[TARGET, ADDRESS]:

  def f(
    target: TARGET
  ): ADDRESS

  extension (target: TARGET)

    def countSources: ADDRESS =

      f(target)
