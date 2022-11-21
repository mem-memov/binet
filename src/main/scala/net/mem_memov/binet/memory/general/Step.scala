package net.mem_memov.binet.memory.general

case class Step[ADDRESS, TRAVERSAL](
  item: Item[ADDRESS],
  traversal: TRAVERSAL
)
