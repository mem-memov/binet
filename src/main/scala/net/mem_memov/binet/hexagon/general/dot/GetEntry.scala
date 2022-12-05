package net.mem_memov.binet.hexagon.general.dot

trait GetEntry[DOT, ENTRY]:

  def f(
    dot: DOT
  ): ENTRY

  extension (dot: DOT)

    def getEntry: ENTRY =

      f(dot)
