package net.mem_memov.binet.hexagon.general.factory

import net.mem_memov.binet.hexagon.general.Position

trait MakeEntry[FACTORY, ADDRESS, ENTRY]:

  def f(
    position: Position,
    path: ADDRESS,
    content: ADDRESS
  ): ENTRY

  extension (factory: FACTORY)

    def makeEntry(
      position: Position,
      path: ADDRESS,
      content: ADDRESS
    ): ENTRY =

      f(position, path, content)

