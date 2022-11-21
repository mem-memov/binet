package net.mem_memov.binet.memory.general.factory

trait InitialTraversal[FACTORY, ADDRESS, ELEMENT, TRAVERSAL]:

  def f(
    newPath: ADDRESS,
    root: ELEMENT
  ): TRAVERSAL

  extension (factory: FACTORY)

    def initialTraversal(
      newPath: ADDRESS,
      root: ELEMENT
    ): TRAVERSAL =

      f(newPath, root)