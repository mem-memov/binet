package net.mem_memov.binet.hexagon.general.head

trait HasTarget[HEAD, TARGET]:

  def f(
    head: HEAD,
    target: TARGET
  ): Boolean

  extension (head: HEAD)

    def hasTarget(
      target: TARGET
    ): Boolean =

      f(head, target)
