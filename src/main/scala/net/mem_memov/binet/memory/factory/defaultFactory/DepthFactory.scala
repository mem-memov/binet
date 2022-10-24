package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.depth.DefaultDepth

trait DepthFactory:

  lazy val emptyDepth: Depth

object DepthFactory:

  def apply(): DepthFactory =

    new DepthFactory:

      override lazy val emptyDepth: Depth = DefaultDepth(0)



