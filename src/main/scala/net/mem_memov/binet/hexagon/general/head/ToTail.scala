package net.mem_memov.binet.hexagon.general.head

trait ToTail[HEAD, TAIL]:

  def f(
    head: HEAD
  ): TAIL

  extension (head: HEAD)

    def toTail: TAIL =

      f(head)