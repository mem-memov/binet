package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.Connection
import zio.*

class BothDotsEmpty() extends Connecting:

  def connect: Task[Connection] = ???
