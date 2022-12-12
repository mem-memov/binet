package net.mem_memov.binet.hexagon.general.target

trait GetAddress[TARGET, ADDRESS]:

  def f(
    target: TARGET
  ): ADDRESS

  extension (target: TARGET)

    def getAddress: ADDRESS =

      f(target)
