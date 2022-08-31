package net.mem_memov.binet.hexagon

import zio.*

trait Connecting:
  def connect: Task[Connection]

