package net.mem_memov.binet.hexagon.general.head

trait HasTargetDot[HEAD, DOT]:

  def f(
    head: HEAD,
    dot: DOT
  ): Boolean

  extension (head: HEAD)

    def hasTargetDot(
      dot: DOT
    ): Boolean =

      f(head, dot)
