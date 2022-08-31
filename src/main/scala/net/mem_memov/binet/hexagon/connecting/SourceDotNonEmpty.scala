package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Connection}
import zio.*

class SourceDotNonEmpty(sourceDotTargetArrow: Arrow) extends Connecting:

  def connect: Task[Connection] = ???
