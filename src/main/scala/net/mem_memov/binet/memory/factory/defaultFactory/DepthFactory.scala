package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.depth.DefaultDepth

trait DepthFactory:

  def makeDepth(number: Int): Depth

object DepthFactory:

  def apply(): DepthFactory = DefaultDepth(_)