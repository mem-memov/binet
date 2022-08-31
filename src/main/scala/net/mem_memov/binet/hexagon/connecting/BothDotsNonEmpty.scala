package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Connection, Dot, Entry, Network}
import net.mem_memov.binet.memory
import zio.*

class BothDotsNonEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot,
  sourceDotTargetArrow: Arrow,
  targetDotSourceArrow: Arrow
) extends Connecting:

  override def connect: Task[Connection] =
    val entry = Entry(
      sourceDot.address,
      sourceDotTargetArrow.address,
      memory.Address.zero,
      targetDot.address,
      targetDotSourceArrow.address,
      memory.Address.zero
    )
    ???