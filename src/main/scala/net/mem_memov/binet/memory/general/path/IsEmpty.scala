package net.mem_memov.binet.memory.general.path

trait IsEmpty[PATH]:

  def isPathEmpty(
    path: PATH
  ): Boolean

  extension (path: PATH)

    def isEmpty: Boolean =

      isPathEmpty(path)
