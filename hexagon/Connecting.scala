package net.mem_memov.binet.memory.hexagon

import zio.*

trait Connecting:
  def connect: Task[Arrow]

