package net.mem_memov.binet.memory.general.path

trait IsEmpty[PATH]:

  private[IsEmpty]
  def f(
    path: PATH
  ): Boolean

  extension (path: PATH)

    def isEmpty: Boolean =

      f(path)
