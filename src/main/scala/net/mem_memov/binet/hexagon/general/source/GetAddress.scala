package net.mem_memov.binet.hexagon.general.source

trait GetAddress[SOURCE, ADDRESS]:

  def f(
    source: SOURCE
  ): ADDRESS

  extension (source: SOURCE)

    def getAddress: ADDRESS =

      f(source)
